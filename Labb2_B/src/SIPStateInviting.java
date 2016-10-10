import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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

    public SIPState receivedTroSendingAck(String message)
    {
        String[] argumets = getArguments(message);


        try {
            if(argumets[1] != null)
            {
                int remoteAudioPort = Integer.parseInt(argumets[1]);
                myAudiosocket.connectTo(clientSocket.getInetAddress(), remoteAudioPort);
                myAudiosocket.startStreaming();
                System.out.println("Got TRO and in INVITING - sending ACK - starting audio stream");
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                out.println("ACK");
                return new SIPStateInSession(clientSocket, myAudiosocket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  error(clientSocket);
    }
}
