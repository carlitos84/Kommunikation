import java.io.*;
import java.net.Socket;

import static java.lang.System.in;

/**
 * Client thread serves the client
 */
public class ClientThread implements Runnable{
    Socket socket;
    Client client;

    public ClientThread(Client client) {
        this.client = client;
        socket = client.getSocket();
    }

    @Override
    public void run() {

        BufferedReader in = null;

        //Create input stream
        in = createInputStream();

        //Create message sender thread
        OutputStream out = createOutputStream(socket);
        //Create sending thread for client (messages -> client)
        Thread senderT = new Thread(new MessageSender(out, client));
        senderT.start();

        //Listen for messages from client (client -> server)
        String messageFromClient = null;

        while(!client.isClientInactive()){
            messageFromClient = listenForMessage(in);
            if(messageFromClient == null) {
                System.out.println("Listen socket returns null, disconnected client");
                synchronized (client) {
                    client.setClientInactive();
                }

            }
            else if(messageFromClient.length() == 0){
                 //Empty message, do nothing
            }
            else {
                CommandHandler.commandHandler(messageFromClient, client);
            }
        }
        //Remove inactive client
        CommandHandler.removeClient(client);

        //Join sender thread
        try {
            senderT.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    private String listenForMessage(BufferedReader in) {
        String message = null;
        try {
            message = in.readLine();
        } catch (IOException e) {
            System.out.println("Client disconnected, flagging as inactive");
            synchronized (client) {
                client.setClientInactive();
            }
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
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            client.setClientInactive();
            e.printStackTrace();
        }
        return in;
    }


    private class MessageSender implements Runnable{

        private Client client;
        private PrintWriter out;

        private MessageSender(OutputStream outputStream, Client client) {
            this.client = client;
            out = new PrintWriter(outputStream, true);
        }
        @Override
        public void run() {
            System.out.println("Client message sender thread started");
            while(!client.isClientInactive()){
                if(client.hasMessageInbox()) {
                    String message = client.getMessageInbox();
                    out.println(message);
                }
            }
        }
    }
}
