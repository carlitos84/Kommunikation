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
                byte[] writebuf = new byte[256];
                byte[] readbuf = new byte[256];

                // receive message
                DatagramPacket packet = new DatagramPacket(readbuf, readbuf.length);
                socket.receive(packet);
                int indexOfClient = isClientKnown(packet.getAddress());
                message = new String(packet.getData(), 0, packet.getLength());

                if(message.charAt(0) == '/' && indexOfClient >= 0)
                {
                    commandHandler(message, indexOfClient);
                }
                if(message.equals("HELLO") && !checkIfClientExist(packet.getAddress()) )
                {
                    //create new client
                    clientList.add(new Client(packet.getAddress(), clientIDCounter++, packet.getPort()));
                    System.out.println("client port: " + (clientList.get(clientList.size()-1)).getPort());
                    System.out.println("list size: " + clientList.size());
                    Thread c = new Thread(new ClientThread(clientList.get(clientList.size()-1)));
                    c.start();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
                serverOn = false;
            }
        }
        socket.close();
    }

    private int isClientKnown(InetAddress clientIp)
    {
        int indexOfClient = -1;
        for(int i = 0;i<clientList.size();i++)
        {
            if(clientList.get(i).getIpAdress().equals(clientIp))
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
            System.out.println("nick: " + message);
        }
        else
        {
            switch (message)
            {
                case "/quit":
                    clientList.get(indexOfClient).setClientInactive();
                    clientList.remove(indexOfClient);
                    for (Client c : clientList)
                    {
                        System.out.println("client: " + c.getNickname());
                    }
                    System.out.println("client " + indexOfClient + "has quitted!");
                    break;
                case "/who":
                    whoCommand(indexOfClient);
                    System.out.println(message);
                    break;
                case "/help":
                    System.out.println(message);
                    break;
                default:
                    System.out.println("error: felkommandon there is no " + message);
                    //fel command
            }
        }

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

    private boolean checkIfClientExist(InetAddress clientIp)
    {
        boolean exist = false;

        for(Client c : clientList)
        {
            if(c.getIpAdress().equals(clientIp))
            {
                exist = true;
            }
        }
        return exist;
    }
}

