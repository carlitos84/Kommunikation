import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Teddy on 2016-10-07.
 */
public class ClientHandler implements Runnable{

    private Socket clientSocket;
    private SIPController controller;
    private boolean running;

    public ClientHandler(Socket clientSocket, SIPController controller)
    {
        this.clientSocket = clientSocket;
        this.controller = controller;
        running =true;
    }

    @Override
    public void run() {
        Thread t = new Thread(new MessageReceiver(clientSocket, controller));
        t.start();
    }

    private class MessageReceiver implements Runnable
    {
        private BufferedReader reader;
        private SIPController control;

        public MessageReceiver(Socket clientSocket, SIPController control)
        {
            System.out.println("in MessageReciever contruct");
            reader = null;
            this.control = control;
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {

            try {
                String message;

                while ((message = reader.readLine()) != null)
                {
                    System.out.println("message from client(in MessageReciever): " + message);
                    control.processNextEventInGoing(message);
                }

            } catch (IOException e) {
                System.out.println("Socket close, closing in progress");
                ClientListener.setNeedToReset(false);
                ClientListener.setBusy(false);
                try {
                    clientSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //e.printStackTrace();
            }

        }
    }
}
