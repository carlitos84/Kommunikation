/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPStateInviting extends SIPState {
    public SIPState recieveTRO()
    {
        return this;
    }
    public SIPState sendACK(){return new SIPStateInSession();}
}
