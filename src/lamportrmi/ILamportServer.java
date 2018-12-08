package lamportrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Nathan
 */
public interface ILamportServer extends Remote {
    public String SERVICE_NAME = "LAMPORT_RMI";
    public void requestAll() throws RemoteException;
    public void responseAll(String ipAdress, int port) throws RemoteException;
    public void freeSC() throws RemoteException;
}
