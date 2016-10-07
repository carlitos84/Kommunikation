import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Teddy on 2016-10-07.
 */
public class ClientListener implements Runnable{

    private static boolean busy = false;
    private ServerSocket serverSocket;
    private boolean running;
    private static InetAddress myIP = null;


    public ClientListener(int port)
    {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            myIP = serverSocket.getInetAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        while(running)
        {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Socket accepted");
                if(isBusy())
                {
                    System.out.println("is BUSY");
                    clientSocket.close();
                }
                else
                {
                    Thread t = new Thread(new ClientHandler(clientSocket));
                    t.run();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static boolean isBusy()
    {
        return busy;
    }

    public static InetAddress getMyIP(){
        return myIP;
    }
}
