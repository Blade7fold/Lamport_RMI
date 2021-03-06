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
     * @param message Le message envoyé avec une requête
     * @throws RemoteException 
     */
    public void request(Message message) throws RemoteException;
    
    /**
     * Envoi du Message RESPONSE après avoir Reçu le Message REQUEST
     * @param message Le message envoyé avec une réponse
     * @throws RemoteException 
     */
    public void response(Message message) throws RemoteException;
    
    /**
     * Envoie du message FREE pour indiquer aux autres serveur que l'on sort de
     * la section critique
     * @param message Le message envoyé pour libérer la section critique
     * @throws RemoteException 
     */
    public void freeSC(Message message) throws RemoteException;
    
    /**
     * Métode pour modifier la variable globalle dans tous les serveurs
     * @param newValue La nouvelle valeur de la variable
     * @throws RemoteException 
     */
    public void setVariableGlobally(int newValue) throws RemoteException;
}
