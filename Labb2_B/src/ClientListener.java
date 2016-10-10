import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
    private final Object lock = new Object();

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
        System.out.println("before t.run");
        t.start();
        System.out.println("before while-loop");
        while(running)
        {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Socket accepted");
                System.out.println("isBusy: " + isBusy());
                if(isBusy())
                {
                    System.out.println("is BUSY");
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                    out.println("BUSY");

                        synchronized (lock)
                        {
                            try{
                                lock.wait(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }finally {
                                clientSocket.close();
                            }
                        }

                }
                else
                {
                    synchronized (this.controller)
                    {
                        this.controller.init(clientSocket);
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

    public static void setBusy(boolean set){
        synchronized (busy)
        {
            busy = set;
        }
    }

    public class MessageSender implements Runnable{

        @Override
        public void run() {
            //write to others
            Scanner scan = new Scanner(System.in);
            while(true)
            {
                System.out.println("while");
                String messageToSend = scan.nextLine();
                System.out.println("sending: " + messageToSend);
                controller.processNextEventOutGoing(messageToSend);
                System.out.println("aftrer controller");
            }
        }
    }

}
