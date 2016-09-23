import com.sun.prism.shader.DrawCircle_ImagePattern_AlphaTest_Loader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientThread implements Runnable{
    private Client client;

    public ClientThread(Client client)
    {
        this.client = client;
    }

    @Override
    public void run() {
        try{
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
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    static synchronized void sendMessageToClient (String message, Client sendingClient) throws IOException {

            byte[] writebuf = new byte[256];
            writebuf = message.getBytes();
            DatagramPacket packet = new DatagramPacket(writebuf, writebuf.length, sendingClient.getIpAdress(), sendingClient.getPort());
            sendingClient.getSocket().send(packet);
    }
}
