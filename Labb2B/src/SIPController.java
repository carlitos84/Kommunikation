import java.net.Socket;

/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPController {
    public enum SIPEvent { INVITE, TRO, OK, BYE, ACK, DEFAULT}
    private static boolean isBusy = false;
    private static SIPState currentState = new SIPStateFree();

    public static synchronized boolean trySetBusy()
    {
        if(isBusy)
        {
            return false;
        }
        return isBusy = true;
    }

    public static synchronized void setToFree()
    {
        isBusy = false;
    }


    public static void processNextEventInGoing(String message, Socket clientSocket)
    {
        SIPEvent event = commandHandlerInGoing(message);
        System.out.println("message: " + message + "event: " + event.name());
        switch (event)
        {
            case INVITE:
                currentState = currentState.invitedSendingTro(clientSocket, message);
                break;
            case TRO:
                currentState = currentState.receivedTroSendingAck();
                break;
            case OK: currentState = currentState.receivedOk();
                break;
            case BYE:
                currentState = currentState.receivedByeSendingOk();
                break;
            case ACK:
                currentState = currentState.receivedAck();
                break;
            default:
                currentState = currentState.error(clientSocket);
                System.out.println("in inGoing default!");
                //if connected, then disconnect
        }
    }

    public static void processNextEventOutGoing(String message)
    {
        System.out.println("message: " + message);
        SIPEvent event = commandHandlerOutGoing(message);
        switch (event)
        {
            case INVITE:
                currentState = currentState.sendingInvite(message);
                break;
            case BYE:
                currentState = currentState.sendingBye();
                break;
            case ACK:
                currentState = currentState.receivedTroSendingAck();
                break;
            default:
                System.out.println("in OutGoing deafult");
                //errorhandler. if connected, then disconnect
        }
    }

    private static SIPEvent commandHandlerInGoing(String command)
    {
        String[] comando = command.split(" ");

        for(SIPEvent e : SIPEvent.values())
        {
            if(e.name().equals(comando[0]))
            {
                return e;
            }
        }
        return SIPEvent.DEFAULT;
    }

    private static SIPEvent commandHandlerOutGoing(String command)
    {
        String[] comando = command.split(" ");

        for(SIPEvent e : SIPEvent.values())
        {
            if(e.name().equals(comando[0]))
            {
                return e;
            }
        }
        return SIPEvent.DEFAULT;
    }
}
