import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Teddy on 2016-10-07.
 */
public class ClientListener implements Runnable{

    private static Boolean busy = false;
    private ServerSocket serverSocket;
    private boolean running;
    private SIPController controller;


    public ClientListener(int port)
    {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            controller = new SIPController(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        Thread t = new Thread(new MessageSender());
        t.run();

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
                    synchronized (this.controller)
                    {
                        this.controller = new SIPController(clientSocket);
                    }
                    this.busy = true;
                    Thread clientHandlerThread = new Thread(new ClientHandler(clientSocket, controller));
                    clientHandlerThread.run();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static boolean isBusy()
    {
        synchronized (busy) {
            return busy;
        }
    }

    public static void setBusy(){
        synchronized (busy)
        {
            busy = true;
        }
    }

    private class MessageSender implements Runnable{

        @Override
        public void run() {
            //write to others
            Scanner scan = new Scanner(System.in);
            while(running)
            {
                String messageToSend = scan.nextLine();
                System.out.println("sending: " + messageToSend);
                controller.processNextEventOutGoing(messageToSend);
            }
        }
    }

}
