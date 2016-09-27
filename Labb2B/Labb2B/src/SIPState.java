/**
 * Created by Teddy on 2016-09-27.
 */
public abstract class SIPState {
    public SIPState sendInvite() {return this;}     // -/INVITE
    public SIPState recieveInvite() {return this;}    //  TRO/ACK
    public SIPState sendTRO(){return this;} //   INVITE/TRO
    public SIPState recieveTRO(){return this;}     //      ACK/-
    public SIPState sendACK(){return this;} // BYE/OK
    public SIPState recieveACK (){return this;}    // -/BYE
    public SIPState sendBYE(){return this;} // OK/-
    public SIPState recieveBYE(){return this;}
    public SIPState sendOK(){return this;}
    public SIPState recieveOK(){return this;}
    public void printState(){;}
}
