package lamportrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Nathan
 */
public interface IGlobalRMI extends Remote {
    public String SERVICE_NAME = "GLOBAL_RMI";
    public int getVariable() throws RemoteException;
    public void setVariable(int newValue) throws RemoteException;
}
