import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Teddy on 2016-09-24.
 */
public class ServerThread extends Thread{
    protected String message;
    protected ServerSocket servSock = null;
    protected ArrayList<Client> clientList;
    protected boolean serverOn;
    protected int clientIDCounter;
    private int port = 4445;

    public ServerThread() throws IOException{
        this("ServerThread");
    }

    public ServerThread(String name) throws IOException
    {
        super(name);
        servSock = new ServerSocket(port);
        clientList = new ArrayList<>();
        serverOn = true;
        clientIDCounter = 0;
    }

    public void run() {
        BufferedReader in = null;
        // Listen to incoming connections, create a separate
        // client handler thread for each connection
        while (serverOn) {
            try {
                System.out.println("Server is listening...");
                //accept the client
                Socket clientSock = servSock.accept();
                System.out.println("Server contacted from " + clientSock.getInetAddress());
                //check if client exist, if there is no such client in list then add client to list otherwise continue.
                if (!checkIfClientExist(clientSock.getInetAddress(), clientSock.getPort())) {
                    System.out.println("new client!");
                    //add client to the list of clients
                    Client newClient = new Client(clientSock, clientIDCounter++);
                    clientList.add(newClient);
                    System.out.println("client amount: " + clientList.size());
                    // Create a new clienthandler thread
                    ClientHandlerThread cl = new ClientHandlerThread(newClient);
                    cl.start();
                }
                //gets index of client. Here the client will have an index because of the register above. Maybe change name to getIndexOfClient()?
                int indexOfClient = isClientKnown(clientSock.getInetAddress(), clientSock.getPort());
                //read messages
                in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
                String messageFromClient = in.readLine();
                System.out.println("client request: " + messageFromClient);
                if (messageFromClient.charAt(0) == '/') {
                    commandHandler(messageFromClient, indexOfClient);
                } else if (clientList.size() > 1) {
                    sendMessageToClients(indexOfClient, messageFromClient);
                }
            }// Close the  server socket...
            catch (IOException e) {

                e.printStackTrace();
                serverOn = false;
            }
        }
        if (servSock != null) try {
            servSock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (in != null) try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
