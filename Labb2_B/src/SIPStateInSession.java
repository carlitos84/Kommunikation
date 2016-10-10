import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPStateInSession extends SIPState {
    private Socket clientSocket;
    private AudioStreamUDP myAudiosocket;

    public SIPStateInSession(Socket clientSocket, AudioStreamUDP audiosocket)
    {
        this.clientSocket = clientSocket;
        this.myAudiosocket = audiosocket;
    }

    public SIPState sendingBye()
    {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("BYE");
            return new SIPStateWaitingToClose(clientSocket, myAudiosocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //errorhandler
        return  error(clientSocket);
    }

    public SIPState receivedByeSendingOk()
    {
        System.out.println(" Recieved BYE and sending OK");
        try {

            myAudiosocket.stopStreaming();
            myAudiosocket.close();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("OK");

            return new SIPStateFree();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //error handler;
        return  error(clientSocket);
    }
}
