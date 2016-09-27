/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPHandler {
    public enum SIPEvent { SEND_INVITE, RECIEVE_INVITE, SEND_TRO, RECIEVED_TRO, SEND_ACK,
        RECIEVE_ACK, SEND_OK, RECIVIE_OK, SEND_BYE, RECIEVE_BYE};

    private SIPState currentState;

    public SIPHandler()
    {
           currentState = new SIPStateFree();
    }

    private void processNextEvent(SIPEvent event)
    {
        switch (event)
        {
            case SEND_INVITE: currentState = currentState.sendInvite();
                break;
            case RECIEVE_INVITE: currentState = currentState.recieveInvite();
                break;
            case SEND_TRO: currentState = currentState.sendTRO();
                break;
            case RECIEVED_TRO: currentState = currentState.recieveTRO();
                break;
            case SEND_ACK: currentState = currentState.sendACK();
                break;
            case RECIEVE_ACK:currentState = currentState.recieveACK();
                break;
            case SEND_BYE: currentState = currentState.sendBYE();
                break;
            case RECIEVE_BYE: currentState = currentState.recieveBYE();
                break;
            case SEND_OK: currentState = currentState.sendOK();
                break;
            case RECIVIE_OK: currentState = currentState.recieveOK();
                break;
            default:
        }
    }

    public void commandHandler(String command)
    {
        String[] comando = command.split(" ");

        for(SIPEvent e : SIPEvent.values())
        {
            if(e.name().equals(comando[0]))
            {
                processNextEvent(e);
            }
        }

    }

}
