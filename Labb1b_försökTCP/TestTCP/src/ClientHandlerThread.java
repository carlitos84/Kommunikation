import com.sun.deploy.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Socket;

/**
 * Created by Teddy on 2016-09-24.
 */
public class ClientHandlerThread extends Thread{
    private PrintWriter sout = null;
    private Client client;

    public ClientHandlerThread(Client client)
    {
        this.client = client;
    }

    // The thread activity, send a single message and then exit.
    @Override
    public void run() {

        try {
            //send message
            sendMessageToClient("Welcome!", client);
            while(!client.isClientInactive())
            {
                if(client.hasMessageForClient())
                {
                    String txt = client.getMessageForClient();
                    System.out.println("send message to client: " + txt);
                    sendMessageToClient(txt, client);
                }
            }
        }
        catch(IOException ie) {
            System.out.print(" IO execption ClientHandlerT");
            System.err.println(ie.toString());
        }
        finally {
            try {
                //if(client.getSocket() != null) client.getSocket().close();
            } catch(Exception e) {
                System.out.println("Execption in CHT");
                e.printStackTrace();
            }
        }
    }

    static synchronized void sendMessageToClient (String message, Client sendingClient) throws IOException{
        // Get a stream for sending messages to client
        // true = auto flush
        PrintWriter sout = new PrintWriter(sendingClient.getSocket().getOutputStream(), true);
        sout.println(message);
    }
}
