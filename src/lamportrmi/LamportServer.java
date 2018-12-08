package lamportrmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nathan
 */
public class LamportServer extends UnicastRemoteObject implements ILamportServer {
    
    List<ServerDAO> servers;
    Map<Integer, ILamportServer> serverMap;
    Message[] stateMessages;
    final String IP_ADRESS;
    final int PORT;
    final int ID;
    
    public LamportServer(List<ServerDAO> servers, ServerDAO ownDAO) throws RemoteException, InterruptedException {
        super();
        IP_ADRESS = ownDAO.getIp_adress();
        PORT = ownDAO.getPort();
        ID = ownDAO.getId();
        
        // initialize la liste de message à libre au départ de tous les serveurs
        stateMessages = new Message[servers.size()];
        for (int i = 0; i < stateMessages.length; i++) {
            stateMessages[i] = Message.FREE;
        }
    }
    
    public void setServers(Map<Integer, ILamportServer> serverMap) {
        this.serverMap = serverMap;
    }
    
    public void askLock() {
        
    }
    
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
