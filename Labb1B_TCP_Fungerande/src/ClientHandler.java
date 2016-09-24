import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Listens for new clients, redirects socket to a thread
 */
public class ClientHandler implements Runnable{

    private ServerSocket socket;
    private boolean running;
    private int guestId;
    private static ArrayList<Client> clientList;

    public ClientHandler(int port) throws IOException{
        socket = new ServerSocket(port);
        running = true;
        guestId = 0;
        clientList = new ArrayList<>();
        CommandHandler.initiateClientList(clientList);
    }


    @Override
    public void run(){
        BufferedReader in = null;

        while(running) {

            Socket clientSocket = listenForClient();

            startClientThread(clientSocket, guestId++);

        }

    }

    private void startClientThread(Socket socket, int guestId){
        if(socket != null){
            //Create a nw client thread
            Client client = new Client(socket, guestId++);
            Thread clientT = new Thread(new ClientThread(client));
            clientT.start();
            clientList.add(client);
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
            writeMessage(clientSocket, "Welcome");

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
}
