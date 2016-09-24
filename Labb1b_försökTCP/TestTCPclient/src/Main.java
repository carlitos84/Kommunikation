import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Executable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Teddy on 2016-09-24.
 */
public class Main {
    public static void main(String args[])
    {
        int port = 4445;
        String serverHostname = args[0];

        if (args.length != 1) {
            System.out.println("Usage: java QuoteClient <hostname>");
            return;
        }
        //creates socket, inStream- and outStream socket.
        Socket clientsocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        /**
         * try-catch metoden kanske bör ändras? i kodexempeln har dom try-catch endast för initiering av clientSocket, in och out.
         * ***/

        try
        {
            clientsocket = new Socket(serverHostname, port);
            out = new PrintWriter(clientsocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));


            Thread receiveT = new Thread(new MessageReceiver(in, serverHostname, port));
            receiveT.start();

            Scanner scan = new Scanner(System.in);
            //Send messages
            boolean quit = false;
            String message;
            while(!quit) {

                 message = scan.nextLine();
                //System.out.println(message);
                if(message.equals(new String("exit"))){
                    quit = true;
                    System.out.println("Exit in progress");
                }
                else {
                    System.out.println("about to send: " + message);
                    out.println(message);
                }

            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            //e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverHostname);
            //e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {

            if(out != null)
            {
                out.close();
            }
            if(in != null)
            {
                try {
                    in.close();
                } catch (IOException e) {
                    System.err.println(" could not close in-socket ");
                    e.printStackTrace();
                }
            }
            if(clientsocket != null)
            {
                try {
                    clientsocket.close();
                } catch (IOException e) {
                    System.err.println(" could not close socket");
                    e.printStackTrace();
                }
            }

        }

    }
}
