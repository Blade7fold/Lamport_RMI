package lamportrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 * Classe qui gère l'algorithme de Lamport
 * 
 * @author Nathan & Jimmy
 */
public class LamportServer extends UnicastRemoteObject implements ILamportServer {
    
    final String IP_ADRESS; // Adresse du serveur
    final int PORT;         // Port du serveur
    final int ID;           // ID du serveur
    
    List<ServerDAO> servers;    // Liste des serveurs disponibles
    Map<Integer, ILamportServer> serverMap; // Map des serveurs
    Message[] stateMessages;    // Messages d'état de la section critique
    //int estampille;
    
    /**
     * Constructeur de la classe LamportServer
     * @param servers Les serveurs lancés
     * @param ownDAO La structure des serveurs
     * @throws RemoteException
     * @throws InterruptedException 
     */
    public LamportServer(List<ServerDAO> servers, ServerDAO ownDAO)
            throws RemoteException, InterruptedException {
        super();
        this.IP_ADRESS = ownDAO.getIpAdress();
        this.PORT = ownDAO.getPort();
        this.ID = ownDAO.getId();
        
        // initialize la liste de message à libre au départ de tous les serveurs
        this.stateMessages = new Message[servers.size()];
        for (int i = 0; i < this.stateMessages.length; i++) {
            this.stateMessages[i] = Message.FREE;
        }
        //this.estampille = 0;
    }
    
    /**
     * Métode pour actualiser les serveurs
     * @param serverMap Une Mao de serveurs
     */
    public void setServers(Map<Integer, ILamportServer> serverMap) {
        this.serverMap = serverMap;
    }
    
    /**
     * Demande de la section critique de la part d'un client
     */
    public void askLock() {
        
    }
    
    /**
     * Fin de la modification de la variable globale dans la section critique
     */
    public void finishLock() {
        
    }

    @Override
    public void requestAll() throws RemoteException {
        
    }

    @Override
    public void responseAll(String ipAdress, int port) throws RemoteException {
        
    }

    @Override
    public void freeSC() throws RemoteException {
        
    }
}
