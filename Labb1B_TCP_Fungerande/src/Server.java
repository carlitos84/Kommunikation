import java.io.IOException;

public class Server {
    public static void main(String args[]) {

        int defaultPort = 4445;
        int port = defaultPort;

        //User defined port number
        if(args.length == 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("\'" +args[0] +"\'" + " is not a valid port number. Using default port: " + defaultPort);
            }
        }
        else {
            System.out.println("Using default port number: " +port);
        }

        //Start client handler thread
        Thread clientListenerT = null;
        try {
            clientListenerT = new Thread(new ClientHandler(port));
            clientListenerT.start();
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                clientListenerT.join();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
