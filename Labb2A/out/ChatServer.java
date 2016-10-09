import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatServer extends UnicastRemoteObject implements Chat {

    //private ArrayList<Notifiable> clientList = null;
    private ArrayList<Client> clientList = null;
    int clientId;

    public ChatServer() throws RemoteException {
        super();
        clientList = new ArrayList<>();
        clientId = 1;
    }

    @Override
    public void sendMessage(String msg, Notifiable n) throws RemoteException{
        if(msg.length() > 0 && msg.charAt(0) == '/'){
            String command[] = msg.split(" ");
            switch(command[0]) {
                case "/help":
                    if( command.length == 1){ helpCommand(n); }
                    else { errorCommand(n); }
                    break;
                case "/nick":
                    if(command.length == 2){
                        if(!nickTaken(command[1])){
                            Client c = getClient(n);
                            if(c != null) {
                                c.setNick(command[1]);
                                c.getNotifiable().notifyMessage("Server: New nick is \'" +command[1] +"\'");
                            }
                        }
                        else {
                            n.notifyMessage("Nick taken, pick another one");
                        }
                    }
                    else { errorCommand(n); }
                    break;
                case "/quit":
                    quitCommand(n);
                    n.notifyMessage("Server: Goodbye!");
                    break;
                case "/who":
                    whoCommand(n);
                    break;
                default:
                    errorCommand(n);
            }
        } else {
            //Send message from client to all others
            String message = getClient(n).nick +": " +msg;
            for (int i = 0; i < clientList.size(); i++) {
                try {
                    if (!clientList.get(i).getNotifiable().equals(n)) {
                        clientList.get(i).getNotifiable().notifyMessage(message);
                    } else {
                        System.out.println("Not sending to sending client, i = " + i);
                    }
                } catch (Exception e) {
                    try {
                        deRegisterClient(clientList.get(i).getNotifiable());
                    } catch (RemoteException e1) {
                        System.out.println("Deregistration failed");
                        e1.printStackTrace();
                    }
                    System.out.println("Send message failed");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void registerClient(Notifiable n) throws RemoteException {
        clientList.add(new Client(clientId++, n));
    }

    @Override
    public void deRegisterClient(Notifiable n) throws RemoteException {
        for(Client c: clientList){
            if(c.getNotifiable().equals(n)){
                clientList.remove(c);
            }
        }

    }

    public static void main(String args[]) {
        try {
            ChatServer server = new ChatServer();
            Naming.rebind("chat", server);
            System.out.println("Server running");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private Client getClient(Notifiable n) {
        for(Client c: clientList){
            if(c.getNotifiable().equals(n)){
                return c;
            }
        }
        return null;
    }

    private void helpCommand(Notifiable n) throws RemoteException{
        String availableCommands = "Server: Commands available:\n/help\n/nick\n/quit\n/who";
        n.notifyMessage(availableCommands);
    }

    private void whoCommand(Notifiable n) throws RemoteException{
        StringBuilder sb = new StringBuilder();
        sb.append("Server :");
        for(Client c: clientList) {
            sb.append(c.getNick() +" ");
        }
        n.notifyMessage(sb.toString());
    }

    private void quitCommand(Notifiable n) throws RemoteException {
        n.notifyMessage("Goodbye");
        deRegisterClient(n);
    }

    private void errorCommand(Notifiable n) throws RemoteException{
        n.notifyMessage("Server: No such command is available");
    }

    private boolean nickTaken(String newNick) {
        for(Client c: clientList) {
            if(c.getNick().equals(newNick)){
                return true;
            }
        }
        return false;
    }

    private class Client {
        String nick;
        Notifiable n;

        private Client(int id, Notifiable n) {
            this.nick = "Guest" +id;
            this.n = n;
        }

        public String getNick() {
            return nick;
        }
        public void setNick(String nick) {
            this.nick = nick;
        }

        private Notifiable getNotifiable(){
            return n;
        }
    }
}
