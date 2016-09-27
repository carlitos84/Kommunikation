/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPStateInvited extends SIPState{
    public SIPState recieveACK()
    {
        return new SIPStateInSession();
    }
}
