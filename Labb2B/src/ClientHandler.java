import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Listens for new clients, redirects socket to a thread
 */
public class ClientHandler implements Runnable{

    private ServerSocket socket;
    private boolean running;
    private SIPController controller;

    public ClientHandler(int port, SIPController controller) throws IOException{
        socket = new ServerSocket(port);
        running = true;
        this.controller = controller;
    }

    @Override
    public void run(){
        BufferedReader in = null;
        //Create sending thread for client (messages -> client)
        Thread senderT = new Thread( new MessageSender());
        senderT.start();

        while(running) {
            Socket clientSocket = listenForClient();
            if(!SIPController.trySetBusy())
            {
                System.out.println("I'am BUSY");
                rejectClient(clientSocket);
            }
            else
            {
                //System.out.println("client connected, thred started");
                startClientThread(clientSocket);
            }
        }

        try {
            senderT.join();
            socket.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void rejectClient(Socket clientSocket)
    {
        try {
            writeMessage(clientSocket, "Busy");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startClientThread(Socket socket){
        if(socket != null){
            //Create a nw client thread
            Thread clientT = new Thread(new ClientThread(socket, controller));
            clientT.start();
        }
        else {
            System.out.println("Client socket is null, failed to create client thread");
        }
    }

    private Socket listenForClient(){
        Socket clientSocket = null;
        try{
            System.out.println("Server listening...");

            clientSocket = socket.accept();


        }catch (IOException e){
            running = false;
            try {
                socket.close();
            } catch (IOException e2) {
                System.out.println("Failed to close socket.");
                e2.printStackTrace();
            }
            e.printStackTrace();
        }
        return clientSocket;
    }

    private void writeMessage(Socket s, String message) throws IOException{
        PrintWriter sout = new PrintWriter(s.getOutputStream(), true);
        sout.println(message);
    }

    private class MessageSender implements Runnable{

        @Override
        public void run() {
            System.out.println("Client message sender thread started");
            Scanner scan = new Scanner(System.in);

            while(true){
                String message = scan.nextLine();
                SIPController.processNextEventOutGoing(message);
            }
        }
    }
}
