import java.io.IOException;
import java.net.Socket;

/**
 * Created by Teddy on 2016-09-27.
 */
public abstract class SIPState {

    public SIPState invitedSendingTro(Socket clientSocket, String message){return this;}
    public SIPState receivedAck(){return this;}
    public SIPState sendingInvite(String arguments){return this;}
    public SIPState receivedTroSendingAck(){return this;}
    public SIPState receivedByeSendingOk(){return this;}
    public SIPState sendingBye(){return this;}
    public SIPState receivedOk(){return this;}
    public SIPState error(Socket socket){
        try {
            socket.close();
            SIPController.setToFree();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SIPStateFree();}
    public void printState(){;}

    protected String[] getArguments(String message)
    {
        String[] argument = message.split(" ");
        return argument;
    }
}
