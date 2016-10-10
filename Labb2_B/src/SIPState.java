import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Teddy on 2016-09-27.
 */
public abstract class SIPState {

    public SIPState invitedSendingTro (PrintWriter out, Socket clientSocket, String message){return this;}
    public SIPState receivedAck(){return this;}
    public SIPState sendingInvite(Socket clientSocket, String arguments){return this;}
    public SIPState receivedTroSendingAck(String message){return this;}
    public SIPState receivedByeSendingOk(){return this;}
    public SIPState sendingBye(){return this;}
    public SIPState receivedOk(){return this;}
    public SIPState error(Socket clientsocket){
        try {
            clientsocket.close();
            ClientListener.setBusy(false);

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
