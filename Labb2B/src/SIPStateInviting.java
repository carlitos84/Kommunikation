import java.io.IOException;
import java.net.Socket;

/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPStateInviting extends SIPState {
    private Socket clientSocket;
    private AudioStreamUDP myAudiosocket;

    public SIPStateInviting(Socket clientSocket, AudioStreamUDP myAudiosocket)
    {
        this.clientSocket = clientSocket;
        this.myAudiosocket = myAudiosocket;
    }

    public SIPState receivedTroSendingAck(Socket clientSocket, String message)
    {
        String[] argumets = getArguments(message);

        int remoteAudioPort = Integer.parseInt(argumets[1]);
        try {
            myAudiosocket.connectTo(clientSocket.getInetAddress(), remoteAudioPort);
            myAudiosocket.startStreaming();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SIPStateInSession(clientSocket, myAudiosocket);
    }
}
