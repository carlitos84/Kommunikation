/**
 * Created by Teddy on 2016-09-27.
 */
public class SIPStateWaitingToClose extends SIPState{

    public SIPState recieveOK()
    {
        return new SIPStateFree();
    }
}
