package lamportrmi;

import java.rmi.RemoteException;

/**
 *
 * @author Nathan
 */
public interface IGlobalRMI {
    public String SERVICE_NAME = "GLOBAL_RMI";
    public int getVariable() throws RemoteException;
    public void setVariable(int newValue) throws RemoteException;
}
