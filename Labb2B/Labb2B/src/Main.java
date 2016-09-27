import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Teddy on 2016-09-27.
 */
public class Main {

    public static void main(String args[])
    {
        try{
            ServerSocket server = null;
            server = new ServerSocket(4445);

            Socket clientsocket = null;
            clientsocket = server.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientsocket.getOutputStream(), true);

            SIPHandler h = new SIPHandler();

            String msg = null;
            out.println(welcomeMessage());
            while(true)
            {
                msg = in.readLine();
                h.commandHandler(msg);
                out.println("Hej, skriv command");
            }

        }catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    private static String welcomeMessage()
    {
        return "Hi, available comamnds are: SEND_INVITE, SEND_TRO, SEND_ACK,\n" +
                " SEND_OK, SEND_BYE";
    }
}
