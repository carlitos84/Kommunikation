import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote{

    public void sendMessage(String msg, Notifiable n) throws RemoteException;


    /*Register/deregister client*/
    public void registerClient(Notifiable n) throws RemoteException;
    public void deRegisterClient(Notifiable n) throws RemoteException;
}
