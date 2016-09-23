import javax.xml.crypto.Data;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {
    private InetAddress ipAdress;
    private String nickname;
    private MessageQueue messageQueue;
    private boolean clientInactive;
    private int port;
    private DatagramSocket socket = null;

    public Client(InetAddress IP, int GuestID, int port, DatagramSocket serverSocket)
    {
        ipAdress = IP;
        nickname = "Guess" + Integer.toString(GuestID);
        messageQueue = new MessageQueue();
        this.port = port;
        clientInactive = false;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return this.nickname;
    }

    public void addMessage(String message)
    {
        synchronized (messageQueue) {
            messageQueue.add(message);
        }
    }

    public String getMessageForClient()
    {
        synchronized (messageQueue) {
            return this.messageQueue.poll();
        }
    }

    public InetAddress getIpAdress()
    {
        return this.ipAdress;
    }

    public int getPort()
    {
        return this.port;
    }

    public void setClientInactive()
    {
        clientInactive = true;
    }

    public boolean isClientInactive()
    {
        return clientInactive;
    }

    public boolean hasMessageForClient()
    {
        synchronized (messageQueue) {
            return !this.messageQueue.isEmpty();
        }
    }

    public DatagramSocket getSocket()
    {
        return this.socket;
    }

    public void changeNickname(String newNickname)
    {
        nickname = newNickname;
    }

}
