import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPStateFree extends SIPState{
    public SIPStateFree()
    {
        ;
    }

    public SIPState sendingInvite(String arguments)
    {
        String[] argument = getArguments(arguments);
        if(argument.length != 3)
        {
            return this;
        }
        else
        {
            try {
                InetAddress clientIP = InetAddress.getByName(argument[1]);
                //making my audio port
                AudioStreamUDP myAudioSocket = new AudioStreamUDP();
                int myAudioPort = myAudioSocket.getLocalPort();
                //sending clietn my audio port
                Socket clientSocket = new Socket(clientIP, Integer.parseInt(argument[2]));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("INVITE " + myAudioPort);

                return new SIPStateInviting(clientSocket, myAudioSocket);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public SIPState invitedSendingTro(Socket clientSocket, String receivedMessage)
    {
        String[] arguments = getArguments(receivedMessage);
        int clientAudioPort = Integer.parseInt(arguments[1]);
        try {
            //make own audio port
            AudioStreamUDP myAudioSocket = new AudioStreamUDP();
            int localAudioPort = myAudioSocket.getLocalPort();
            //send to client TRO and my audioPort.
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("sending To " + clientSocket.getInetAddress());
            out.println("TRO " + localAudioPort);

            return new SIPStateInvited(clientSocket, myAudioSocket, clientAudioPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Invitation recieved and sending TRO");
        return this;
    }
}
