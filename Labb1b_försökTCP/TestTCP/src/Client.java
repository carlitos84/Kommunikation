import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private InetAddress ipAdress;
    private String nickname;
    private MessageQueue messageQueue;
    private boolean clientInactive;
    private int port;
    private Socket socket = null;

    public Client(Socket clientsocket, int GuestID)
    {
        ipAdress = clientsocket.getInetAddress();
        nickname = "Guess" + Integer.toString(GuestID);
        messageQueue = new MessageQueue();
        this.port = clientsocket.getPort();
        clientInactive = false;
        this.socket = clientsocket;
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

    public Socket getSocket()
    {
        return this.socket;
    }

    public void changeNickname(String newNickname)
    {
        nickname = newNickname;
    }

}
