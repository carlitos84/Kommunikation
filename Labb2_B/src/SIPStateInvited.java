import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPStateInvited extends SIPState {
    private Socket clientSocket;
    private AudioStreamUDP myAudiosocket;
    private int clientAudioPort;

    public SIPStateInvited(Socket clientSocket, AudioStreamUDP myAudiosocket, int clientAudioPort)
    {
        this.clientSocket = clientSocket;
        this.myAudiosocket = myAudiosocket;
        this.clientAudioPort = clientAudioPort;
    }

    public SIPState receivedAck()
    {
        System.out.println("Received ACK now going in Session");
        try {
            myAudiosocket.connectTo(clientSocket.getInetAddress(), clientAudioPort);
            myAudiosocket.startStreaming();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SIPStateInSession(clientSocket,myAudiosocket);
    }
}
