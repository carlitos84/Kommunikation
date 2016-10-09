import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Notifiable extends Remote {
    public void notifyMessage(String msg) throws RemoteException;
}
