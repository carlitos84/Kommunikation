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
            sendMessageToClient("Welcome!");
            while(!client.isClientInactive())
            {
                if(client.hasMessageForClient())
                {
                    String txt = client.getMessageForClient();
                    System.out.println("send message to client: " + txt);
                    sendMessageToClient(txt);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void sendMessageToClient (String message) throws IOException {

            byte[] writebuf = new byte[256];
            writebuf = message.getBytes();
            DatagramPacket packet = new DatagramPacket(writebuf, writebuf.length, client.getIpAdress(), client.getPort());
            client.getSocket().send(packet);
    }
}
