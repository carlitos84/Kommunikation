import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPController {
    public enum SIPEvent { INVITE, TRO, OK, BYE, ACK, DEFAULT}
    private  SIPState currentState;
    private Socket clientSocket;
    private PrintWriter out;
    private Thread clientHandler;

    public SIPController(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
        this.out = null;
        this.clientHandler = null;
        this.currentState = new SIPStateFree();
    }

    public void init(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("clientSockets IP: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processNextEventInGoing(String message)
    {
        SIPEvent event = commandHandlerEvent(message);
        System.out.println("message from client: " + message + " event: " + event.name());
        try{

            switch (event)
            {
                case INVITE:
                    System.out.println("in INVITE by client and msg is: " + message);
                    currentState = currentState.invitedSendingTro(out,clientSocket, message);
                    break;
                case TRO:
                    System.out.println("REceiving TRO by client");
                    currentState = currentState.receivedTroSendingAck(message);
                    break;
                case OK: currentState = currentState.receivedOk();
                    break;
                case BYE:
                    System.out.println("Receiving BYE by client");
                    currentState = currentState.receivedByeSendingOk();
                    break;
                case ACK:
                    currentState = currentState.receivedAck();
                    break;
                default:
                    System.out.println("in inGoing default!,");
                    if(clientHandler != null)
                    {
                        this.clientHandler.join();
                    }
                    currentState = currentState.errorState(clientSocket, null);
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION inGOing");
            e.printStackTrace();
        }

    }

    public  void processNextEventOutGoing(String message)
    {
        System.out.println("message: " + message);
        SIPEvent event = commandHandlerEvent(message);

        try{
            switch (event)
            {
                case INVITE:
                    System.out.println("Sending INVITE");

                    if(clientSocket == null || clientSocket.isClosed())
                    {
                        System.out.println("clientSocket is null");
                        String[] argument = getArguments(message);
                        InetAddress clientIP = null;
                        try {
                            clientIP = InetAddress.getByName(argument[1]);
                            //InetAddress cIP = InetAddress.getByName("130.229.189.248");
                            Socket clientSocket = new Socket(clientIP, Integer.parseInt(argument[2]));
                            //initiate, gives valur to clientSocket and PrintWriter out.
                            init(clientSocket);
                            clientHandler =  new Thread(new ClientHandler(clientSocket, this));
                            clientHandler.start();
                            ClientListener.setBusy(true);
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    currentState = currentState.sendingInvite(clientSocket, message);

                    break;
                case BYE:
                    System.out.println("Sending BYE");
                    currentState = currentState.sendingBye();
                    break;
                case ACK:
                    System.out.println("Seding ACK");
                    currentState = currentState.receivedTroSendingAck(message);
                    break;
                default:
                    System.out.println("in OutGoing deafult");
                    //errorhandler. if connected, then disconnect
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION inGOing");
            e.printStackTrace();
        }


    }

    private  SIPEvent commandHandlerEvent(String command)
    {
        String[] comando = command.split(" ");

        for(SIPEvent e : SIPEvent.values())
        {
            if(e.name().equals(comando[0]))
            {
                return e;
            }
        }
        System.out.println("In commanHandlerInGoing: " + command);
        return SIPEvent.DEFAULT;
    }

    private String[] getArguments(String message)
    {
        String[] argument = message.split(" ");
        return argument;
    }
}
