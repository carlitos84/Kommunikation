import java.io.*;
import java.net.Socket;

/**
 * Client thread serves the client
 */
public class ClientThread implements Runnable{
    Socket clientSocket;
    boolean activeClient;
    SIPController controller;

    public ClientThread(Socket clientSocket, SIPController controller) {
        this.clientSocket = clientSocket;
        activeClient = true;
        this.controller = controller;
    }

    @Override
    public void run() {

        BufferedReader in = null;

        //Create input stream
        in = createInputStream();

        //Create message sender thread
        OutputStream out = createOutputStream(clientSocket);


        //Listen for messages from client (client -> server)
        String messageFromClient = null;

        while(activeClient){
            messageFromClient = listenForMessage(in);

            if(messageFromClient == null) {
                System.out.println("Listen socket returns null, disconnected client");
                //stati boolean
                SIPController.setToFree();
                activeClient = false;
            }
            else {
                controller.processNextEventInGoing(messageFromClient, clientSocket);
            }
        }

        //Join sender thread
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private String listenForMessage(BufferedReader in) {
        String message = null;
        try {
            message = in.readLine();
        } catch (IOException e) {
            System.out.println("Client disconnected, flagging as inactive");
            activeClient = false;
        }
        if ( message != null) {
            System.out.println(message);
        }
        return message;
    }

    private OutputStream createOutputStream(Socket socket) {
        OutputStream out = null;
        try {
            out = socket.getOutputStream();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    private BufferedReader createInputStream(){
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }



}
