package lamportrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe qui permet aux classes qui l'étende de gérer une section critique
 * avec les méthode askLock et finishLock
 * le tout implémenté via l'algorithme de Lamport
 * 
 * @author Nathan & Jimmy
 */
public class LamportServer extends UnicastRemoteObject implements ILamportServer {
    
    final String IP_ADRESS; // Adresse du serveur
    final int PORT;         // Port du serveur
    final int ID;           // ID du serveur
    
    List<ServerDAO> servers;    // Liste d'information sur les serveurs connectés
    Map<Integer, ILamportServer> serverMap; // Map des serveurs pour communiquer en RMI
    Message[] stateMessages;    // Messages tableau d'état pour chaque serveur utile pour implémenter Lamport
    
    /**
     * Constructeur de la classe LamportServer
     * @param servers Liste d'information sur les serveurs connectés
     * @param ownDAO informations sur notre propre serveur
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
        long time = System.currentTimeMillis();
        for (int i = 0; i < this.stateMessages.length; i++) {
            this.stateMessages[i] = new Message(i, time, MessageType.FREE);
        }
    }
    
    /**
     * Métode pour actualiser les serveursqui doit être appelé
     * une fois que tous les serveurs sont effectivement connectés
     * @param serverMap Une Map de serveurs
     */
    public void setServers(Map<Integer, ILamportServer> serverMap) {
        this.serverMap = serverMap;
    }
    
    /**
     * Demande de la section critique de la part d'un client
     */
    public void askLock() {
        // envoyer à tous request()
        long time = System.currentTimeMillis();
        Message message = new Message(ID, time, MessageType.REQUEST);
        for (Map.Entry<Integer, ILamportServer> entry : serverMap.entrySet()) {
            Integer id = entry.getKey();
            ILamportServer server = entry.getValue();
            try {
                if (id != ID) {
                    server.request(message);
                } else {
                    stateMessages[id] = message;
                }
            } catch (RemoteException ex) {
                System.out.println(ex);
                // Selon la donnée on considère que le réseau est entièrement 
                // fiable et donc que ce catch ne sera jamais appelé
            }
        }
        
        while(!permission()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                
            }
        }
    }
    
    private boolean permission() {
        boolean accord = true;
        for (ServerDAO server : servers) {
            int id = server.getId();
            if (id != ID) {
                accord = accord && (stateMessages[ID].getDate() < stateMessages[id].getDate() || (stateMessages[ID].getDate() == stateMessages[id].getDate() && ID < id));
            }
        }
        return accord;
    }
    
    /**
     * Fin de la modification de la variable globale dans la section critique
     */
    public void finishLock() {
        long time = System.currentTimeMillis();
        Message message = new Message(ID, time, MessageType.FREE);
        for (Map.Entry<Integer, ILamportServer> entry : serverMap.entrySet()) {
            Integer id = entry.getKey();
            ILamportServer server = entry.getValue();
            try {
                if (id != ID) {
                    server.freeSC(message);
                } else {
                    stateMessages[id] = message;
                }
            } catch (RemoteException ex) {
                System.out.println(ex);
                // Selon la donnée on considère que le réseau est entièrement 
                // fiable et donc que ce catch ne sera jamais appelé
            }
        }
    }

    @Override
    public void request(Message message) throws RemoteException {
        int id = message.getFrom();
        if (message.getType() == MessageType.REQUEST) {
            stateMessages[id] = message;
            long time = System.currentTimeMillis();
            Message responseMsg = new Message(ID, time, MessageType.RESPONSE);
            serverMap.get(id).response(responseMsg);
        }
    }

    @Override
    public void response(Message message) throws RemoteException {
        int id = message.getFrom();
        if (message.getType() == MessageType.RESPONSE) {
            if (stateMessages[id].getType() != MessageType.REQUEST) {
                stateMessages[id] = message;
            }
        }
    }

    @Override
    public void freeSC(Message message) throws RemoteException {
        int id = message.getFrom();
        if (message.getType() == MessageType.FREE) {
            if(stateMessages[id].getDate() < message.getDate()) {
                stateMessages[id] = message;
            }
        }
    }
}
