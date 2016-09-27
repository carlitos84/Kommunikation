/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPStateFree extends SIPState{
    public SIPStateFree()
    {
        ;
    }

    public SIPState sendInvite()
    {
        System.out.println("Invite has sended"); return new SIPStateInviting();
    }

    public SIPState recieveInvite()
    {
        return this;
    }

    public SIPState sendTRO()
    {
        return new SIPStateInvited();
    }
}
