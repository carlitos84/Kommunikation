/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPStateInSession extends SIPState {
    public SIPStateInSession()
    {
        ;
    }

    public SIPState sendBYE()
    {
        return new SIPStateWaitingToClose();
    }
    public SIPState recieveBYE(){return this;}
    public SIPState sendOK()
    {
        return new SIPStateFree();
    }
}
