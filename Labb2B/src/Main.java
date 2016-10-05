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
            SIPController controller = new SIPController();
            Thread t = new Thread(new ClientHandler(4445, controller));
            t.run();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
