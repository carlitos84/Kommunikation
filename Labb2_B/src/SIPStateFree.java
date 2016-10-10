import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPStateFree extends SIPState {
    public SIPStateFree()
    {
        showState();
    }

    public SIPState sendingInvite(Socket clientSocket, String arguments)
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
                System.out.println("creating myAudioport: " + myAudioPort);
                //sending client my audio port
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("INVITE " + myAudioPort);
                clientSocket.setSoTimeout(3000);
                System.out.println("socket Timeout: " + clientSocket.getSoTimeout() );
                return new SIPStateInviting(clientSocket, myAudioSocket);
            }catch (SocketTimeoutException e)
            {
                e.printStackTrace();
            }
            catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e)
            {
                throw e;
            }
        }
        return  errorState(clientSocket, null);
    }

    public SIPState invitedSendingTro(PrintWriter out, Socket clientSocket, String receivedMessage)
    {
        System.out.println("before try in invitedSendingTro");
        try {
            String[] arguments = getArguments(receivedMessage);
            //getting clientAudio port:
            int clientAudioPort = Integer.parseInt(arguments[1]);
            System.out.println("clienntAudioPort: " + clientAudioPort);

            //make own audio port
            AudioStreamUDP myAudioSocket = new AudioStreamUDP();
            int localAudioPort = myAudioSocket.getLocalPort();
            //send to client TRO and my audioPort.
            System.out.println("sending To " + clientSocket.getInetAddress());
            out.println("TRO " + localAudioPort);

            return new SIPStateInvited(clientSocket, myAudioSocket, clientAudioPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  errorState(clientSocket, null);
    }
}
