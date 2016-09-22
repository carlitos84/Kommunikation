import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Teddy on 2016-09-22.
 */
public class Client {
    private InetAddress ipAdress;
    private String nickname;
    private MessageQueue messageQueue;
    private boolean clientInactive;
    private int port;

    public Client(InetAddress IP, int GuestID, int port)
    {
        ipAdress = IP;
        nickname = "Guess" + Integer.toString(GuestID);
        messageQueue = new MessageQueue();
        this.port = port;
        clientInactive = false;
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
        messageQueue.add(message);
    }

    public String getMessageForClient()
    {
        return this.messageQueue.poll();
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
        return this.messageQueue.isEmpty();
    }

}
