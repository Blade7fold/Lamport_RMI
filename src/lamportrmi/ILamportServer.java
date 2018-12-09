package lamportrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface de l'algorithme de Lamport
 *
 * @author Nathan & Jimmy
 */
public interface ILamportServer extends Remote {
    public String SERVICE_NAME = "LAMPORT_RMI"; // Nom du service
    
    /**
     * Envoie un Message REQUEST aux autres serveurs pour annoncer le fait de 
     * vouloir rentrer dans la section critique
     * @throws RemoteException 
     */
    public void requestAll() throws RemoteException;
    
    /**
     * Réception du Message RESPONSE après avoir envoyer le Message REQUEST
     * @param ipAdress Adresse du message RESPONSE
     * @param port Port du message RESPONSE
     * @throws RemoteException 
     */
    public void responseAll(String ipAdress, int port) throws RemoteException;
    
    /**
     * Envoie du message FREE après avoir modifier et sortir de la section critique
     * @throws RemoteException 
     */
    public void freeSC() throws RemoteException;
}
