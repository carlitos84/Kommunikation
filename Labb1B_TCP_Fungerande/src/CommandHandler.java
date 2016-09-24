import java.util.ArrayList;

public class CommandHandler {

    private static ArrayList<Client> clientList;

    public synchronized static void commandHandler(String command, Client executingClient) {
        synchronized (clientList) {
            executeCommand(command, executingClient);
        }
    }

    public static void initiateClientList(ArrayList<Client> list){
        clientList = new ArrayList<>();
        synchronized (clientList) {
            clientList = list;
        }
    }

    private static void executeCommand(String message, Client client) {
        int indexOfClient = clientList.indexOf(client);
        String output;

        System.out.println("Index of client = " +indexOfClient);

        if(message.length() > 5 && message.substring(0,5).equals("/nick") && message.charAt(5) == ' ')
        {
            changeClientNickname(client, message);
        }
        else if (message.charAt(0) == '/'){
            switch (message) {
                case "/quit":
                    clientList.get(indexOfClient).setClientInactive();
                    Client cli = clientList.remove(indexOfClient);
                    for (Client c : clientList) {
                        System.out.println("client: " + c.getNickname());
                    }
                    System.out.println("client " + cli.getNickname() + " has quitted!");
                    break;
                case "/who":

                    whoCommand(indexOfClient);
                    System.out.println(message);
                    break;
                case "/help":
                    helpCommand(indexOfClient);
                    System.out.println(message);
                    break;
                default:
                    clientList.get(indexOfClient).addMessage("error: felkommandon there is no " + message);
                    System.out.println("error: client has typed wrong command " + message);
            }
        }
        else {
            System.out.println("Sending to all: " +message);
            sendToAll(message, client);
        }

    }

    public static void removeClient(Client client){
        synchronized (clientList) {
            clientList.remove(client);
        }
    }

    private static void changeClientNickname(Client client, String command) {
        int indexOfClient = clientList.indexOf(client);
        if(!nicknameTaken(command, client))
        {
            String newNickname = command.substring(6,command.length());
            clientList.get(indexOfClient).changeNickname(newNickname);
            clientList.get(indexOfClient).addMessage("Your nickmane is now: " + newNickname);
            System.out.println("client newnick: " + newNickname);
        }
        else
        {
            clientList.get(indexOfClient).addMessage("The nickname is taken.");
        }
    }

    private static boolean nicknameTaken(String msg, Client client)
    {
        int index = clientList.indexOf(client);
        boolean nickExist = false;
        if(msg.length() != 6 && msg.charAt(5) == ' ')
        {
            String nickname = msg.substring(6, msg.length());
            for(Client c : clientList)
            {
                if(c.getNickname().equals(nickname))
                {
                    nickExist = true;
                }
            }
        }
        else
        {
            clientList.get(index).addMessage("Wrong Command!");
        }

        return nickExist;
    }

    private static void whoCommand(int clientIndex) {
        Client executerClient = clientList.get(clientIndex);
        String executerNick = executerClient.getNickname();

        synchronized (clientList) {
            for (Client c : clientList) {
                executerClient.addMessage(c.getNickname());
            }
        }
    }

    private static void helpCommand(int clientIndex) {
        String allCommands = "/quit\n/who\n/nick <nickname>\n/help";
        synchronized (clientList) {
            clientList.get(clientIndex).addMessage(allCommands);
        }
    }

    private static void sendToAll(String message, Client client) {
        int index = clientList.indexOf(client);
        String sourceName = client.getNickname();
        for(int i = 0; i < clientList.size(); i++) {
            if(i != index) {
                clientList.get(i).addMessage(sourceName+ ": " +message);
            }
        }
    }

}
