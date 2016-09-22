import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientThread implements Runnable{
    private Client client;
    private DatagramSocket socket;

    public ClientThread(Client client)
    {
        this.client = client;
        try {
            System.out.println("before");
            socket = new DatagramSocket(client.getPort());
            System.out.println("after");
        } catch (SocketException e) {
            e.printStackTrace();
        }
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
                    System.out.println("client message: " + txt);
                    sendMessageToClient(txt);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            socket.close();
        }
    }

    private void sendMessageToClient (String message) throws IOException {
            byte[] writebuf = new byte[256];
            writebuf = message.getBytes();
            DatagramPacket packet = new DatagramPacket(writebuf, writebuf.length, client.getIpAdress(), client.getPort());
            socket.send(packet);
    }
}
