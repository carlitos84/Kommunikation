import java.io.IOException;
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
        showState();
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
            return new SIPStateInSession(clientSocket,myAudiosocket);
        } catch (IOException e) {
            e.printStackTrace();
            return  errorState(clientSocket, myAudiosocket);
        }

    }
}
