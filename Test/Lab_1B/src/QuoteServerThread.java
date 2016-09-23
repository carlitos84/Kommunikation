import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class QuoteServerThread extends Thread {

    protected DatagramSocket socket = null;
    protected String message;
    protected ArrayList<Client> clientList;
    protected boolean serverOn;
    protected int clientIDCounter;


    public QuoteServerThread() throws IOException {
        this("QuoteServerThread");
    }

    public QuoteServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(4445);
        clientList = new ArrayList<>();
        serverOn = true;
        clientIDCounter = 0;
    }

    public void run() {
        while (serverOn) {
            try {
                byte[] readbuf = new byte[256];

                // receive message
                DatagramPacket packet = new DatagramPacket(readbuf, readbuf.length);

                socket.receive(packet);
                int indexOfClient = isClientKnown(packet.getAddress(), packet.getPort());
                message = new String(packet.getData(), 0, packet.getLength());

                if(message.length() == 0)
                {
                    System.out.println("message is empty!");
                }
                else if(message.charAt(0) == '/' && indexOfClient >= 0)
                {
                    commandHandler(message, indexOfClient);
                }
                else if(message.equals("HELLO") && !checkIfClientExist(packet.getAddress(), packet.getPort()) )
                {
                    //create new client
                    clientList.add(new Client(packet.getAddress(), clientIDCounter++, packet.getPort(), socket));
                    System.out.println("client port: " + (clientList.get(clientList.size()-1)).getPort());
                    System.out.println("list size: " + clientList.size());
                    Thread c = new Thread(new ClientThread(clientList.get(clientList.size()-1)));
                    c.start();
                }
                else if(indexOfClient >=0 )
                {
                    sendMessageToClients(indexOfClient, message);
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
                serverOn = false;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    private void sendMessageToClients(int clientIndex, String msg)
    {
        String msgToSend = clientList.get(clientIndex).getNickname().concat(": " + msg);
        for(int i = 0;i< clientList.size();i++)
        {
            if(i != clientIndex)
            {
                clientList.get(i).addMessage(msgToSend);
            }
        }
    }

    private int isClientKnown(InetAddress clientIp, int clientPort)
    {
        int indexOfClient = -1;
        for(int i = 0;i<clientList.size();i++)
        {
            if(clientList.get(i).getIpAdress().equals(clientIp) && clientList.get(i).getPort() == clientPort)
            {
                return i;
            }
        }
        return indexOfClient;
    }

    private void commandHandler(String message, int indexOfClient)
    {
        if(message.length() > 5 && message.substring(0,5).equals("/nick"))
        {
            changeClientNickname(indexOfClient, message);
        }
        else
        {
            switch (message)
            {
                case "/quit":
                    clientList.get(indexOfClient).setClientInactive();
                    Client cli = clientList.remove(indexOfClient);
                    for (Client c : clientList)
                    {
                        System.out.println("client: " + c.getNickname());
                    }
                    System.out.println("client " + cli.getNickname() + " has quitted!");
                    break;
                case "/who":
                    whoCommand(indexOfClient);
                    System.out.println(message);
                    break;
                case "/help":
                    helpCommand(indexOfClient);
                    System.out.println(message);
                    break;
                default:
                    clientList.get(indexOfClient).addMessage("error: felkommandon there is no " + message);
                    System.out.println("error: client has typed wrong command " + message);
            }
        }
    }

    private void changeClientNickname(int indexOfClient, String message)
    {
        if(!nicknameTaken(indexOfClient, message))
        {
            String newNickname = message.substring(6,message.length());
            clientList.get(indexOfClient).changeNickname(newNickname);
            clientList.get(indexOfClient).addMessage("Your nickmane is now: " + newNickname);
            System.out.println("client newnick: " + message);
        }
        else
        {
            clientList.get(indexOfClient).addMessage("The nickname is taken.");
        }
    }

    private boolean nicknameTaken(int index, String msg)
    {
        boolean nickExist = false;
        if(msg.length() != 6 && msg.charAt(5) == ' ')
        {
            String nickname = msg.substring(6, msg.length());
            for(Client c : clientList)
            {
               if(c.getNickname().equals(nickname))
               {
                    nickExist = true;
               }
            }
        }
        else
        {
            clientList.get(index).addMessage("Wrong Command!");
        }

        return nickExist;
    }

    private void helpCommand(int index)
    {
        String allCommands = "/quit\n/who\n/nick <nickname>\n/help";
        clientList.get(index).addMessage(allCommands);
    }

    private void whoCommand(int clientIndex)
    {
        Client cli = clientList.get(clientIndex);
        cli.addMessage("Active clients:");
        for(Client c : clientList)
        {
            clientList.get(clientIndex).addMessage(c.getNickname());
        }
    }

    private boolean checkIfClientExist(InetAddress clientIp, int clientPort)
    {
        boolean exist = false;

        for(Client c : clientList)
        {
            if(c.getIpAdress().equals(clientIp) && c.getPort() == clientPort)
            {
                exist = true;
            }
        }
        return exist;
    }
}

