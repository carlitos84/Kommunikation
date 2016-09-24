import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client has all info needed to serve a client
 */
public class Client {
    private String nickname;
    private MessageQueue messageQueue;
    private boolean clientInactive;
    private Socket socket = null;

    public Client(Socket clientsocket, int GuestID)
    {
        nickname = "Guest" + Integer.toString(GuestID);
        messageQueue = new MessageQueue();
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

    public String getMessageInbox()
    {
        synchronized (messageQueue) {
            return this.messageQueue.poll();
        }
    }

    public void setClientInactive()
    {
        clientInactive = true;
    }

    public boolean isClientInactive()
    {
        return clientInactive;
    }

    public boolean hasMessageInbox()
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