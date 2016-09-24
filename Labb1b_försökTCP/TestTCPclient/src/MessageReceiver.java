import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class MessageReceiver implements Runnable {

    private BufferedReader readerIn;
    private String hostName;
    private int port;

    public MessageReceiver(BufferedReader reader, String hostname, int port) {
        this.readerIn = reader;
        this.hostName = hostname;
        this.port = port;
    }

    @Override
    public void run(){
       boolean recieve = true;
        while(recieve){
            /**
             * Här ska kanske readerIn in-socket:en vara synchronized istället för System.out?
             * **/
            String messageFromServer = null;
                try{
                    synchronized (readerIn) {

                        messageFromServer = readerIn.readLine();
                    }
                }catch (IOException e)
                {
                    recieve = false;
                    System.err.println("Connection to server failed");
                }

            synchronized (System.out) {
                if(messageFromServer != null)
                {
                    System.out.println();
                    System.out.println("Server: " + messageFromServer);
                }
            }
        }
    }
}
