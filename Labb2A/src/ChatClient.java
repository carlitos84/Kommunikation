import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements Notifiable{
    private Chat chat;

    public ChatClient(Chat chat) throws RemoteException{
        super();
        this.chat = chat;
    }

    /**
     * Used by Char server for callback
     */
    @Override
    public void notifyMessage(String msg) throws RemoteException {
       System.out.println(msg);
    }

    public static void main(String args[]) {
        System.out.println("Chat client started");
        if(args.length != 1){
            System.out.println("Server ip is needed as argument");
            System.exit(0);
        }
        try {
            String url = "rmi://" +args[0] +"/chat";
            Chat chat = (Chat) Naming.lookup(url);
            ChatClient chatClient = new ChatClient(chat);

            /* Register at server */
            chat.registerClient(chatClient);
            chatClient.runClient();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void runClient() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        String msg;
        boolean running = true;
        while(running) {
            msg = scan.nextLine();
            if(msg.equals("/quit")) {
                running = false;
            }
            else {
                try {
                    chat.sendMessage(msg, this);
                } catch (RemoteException e) {
                    System.out.println("Error, exit in progress");
                    running = false;
                    e.printStackTrace();
                }
            }
        }

        /* Deregister */

        try {
            chat.deRegisterClient(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
