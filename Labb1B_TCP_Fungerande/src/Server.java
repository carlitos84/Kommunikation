import java.io.IOException;

public class Server {
    public static void main(String args[]) {

        try {
            Thread clientListenerT = new Thread(new ClientHandler(4445));
            clientListenerT.start();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
