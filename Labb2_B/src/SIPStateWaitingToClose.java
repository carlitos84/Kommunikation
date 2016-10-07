import java.net.Socket;

/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPStateWaitingToClose extends SIPState {
    private Socket clientSocket;
    private AudioStreamUDP myAudiosocket;
    public SIPStateWaitingToClose(Socket clientSocket, AudioStreamUDP myAudiosocket)
    {
        this.clientSocket = clientSocket;
        this.myAudiosocket = myAudiosocket;
    }

    public SIPState receivedOk()
    {
        myAudiosocket.stopStreaming();
        myAudiosocket.close();
        System.out.println("OK received now going to free state");
        return new SIPStateFree();
    }
}
