package lamportrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface globale de RMI
 * 
 * @author Nathan & Jimmy
 */
public interface IGlobalRMI extends Remote {
    public String SERVICE_NAME = "GLOBAL_RMI"; // Nom du service
    
    /**
     * Métode pour connaître la valeur de la variable globale
     * @return La valeur de la variable
     * @throws RemoteException 
     */
    public int getVariable() throws RemoteException;
    
    /**
     * Métode pour modifier la variable globale
     * @param newValue La nouvelle valeur à être écrite
     * @throws RemoteException 
     */
    public void setVariable(int newValue) throws RemoteException;
}
