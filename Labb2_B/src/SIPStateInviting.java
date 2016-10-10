import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPStateInviting extends SIPState {
    private Socket clientSocket;
    private AudioStreamUDP myAudiosocket;

    public SIPStateInviting(Socket clientSocket, AudioStreamUDP myAudiosocket)
    {
        try {
            System.out.println("clientTimeout: " + clientSocket.getSoTimeout());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        showState();
        this.clientSocket = clientSocket;
        this.myAudiosocket = myAudiosocket;
    }

    public SIPState receivedTroSendingAck(String message)
    {
        String[] argumets = getArguments(message);
        try {
            clientSocket.setSoTimeout(0);
            System.out.println("timeout: " + clientSocket.getSoTimeout());
        } catch (SocketException e) {
            e.printStackTrace();
        }

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
            return  errorState(clientSocket, myAudiosocket);
        }
        return  errorState(clientSocket, myAudiosocket);
    }
}
