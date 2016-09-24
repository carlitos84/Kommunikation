import java.io.*;
import java.net.*;

public class EchoClientExempel {
    public static void main(String[] args) throws IOException {


        String serverHostname = args[0];
	int port = 4445;//Integer.parseInt(args[1]);

        Socket echoSocket = null; PrintWriter out = null; BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, port);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + serverHostname);
            System.exit(1);
        }

	BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in));
	String userInput;

        System.out.print ("input: ");
	while ((userInput = stdIn.readLine()) != null) {
	    //write to server
        out.println(userInput);
	    //recieve from server
        System.out.println("echo: " + in.readLine());
        //writes "input:"
            System.out.print ("input: ");
	}

	out.close(); in.close(); stdIn.close(); echoSocket.close();
    }
}

