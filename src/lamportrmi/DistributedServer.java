package lamportrmi;

import java.io.IOException;
import static java.lang.System.exit;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author Nathan
 */
public class DistributedServer extends LamportServer implements IGlobalRMI {
    private int globalVariable;
    private List<DistributedServer> serverPool;

//    public DistributedServer(List<DistributedServer> serverPool) {
//        this.serverPool = serverPool;
//    }

    public DistributedServer(List<ServerDAO> servers, ServerDAO ownDAO) throws RemoteException, InterruptedException {
        super(servers, ownDAO);
    }
    
    
    
    public int getVariable() throws RemoteException {
        return globalVariable;
    }
    
    public void setVariable(int newValue) throws RemoteException {
        this.globalVariable = newValue;
    }
    
    public static void main (String[] args) throws RemoteException, AlreadyBoundException, InterruptedException{
        //String own_ip_adress = args[0];
        //int own_port = Integer.parseInt(args[1]);
        
        /****************************************/
        String own_ip_adress = "127.0.0.1";
        //int own_port = 1099;
        //int own_port = 1100;
        int own_port = 1101;
        /****************************************/
        
        // lecture de la topologie et création d'une liste lui correspondant
        final String delimiter = " ";
        final String fileName = "./structure.txt";
        List<ServerDAO> serverList = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach((line) -> {
                String[] temp = line.split(delimiter);
                String ip_adress = temp[0];
                int port = Integer.parseInt(temp[1]);
                int id = Integer.parseInt(temp[2]);
                serverList.add(new ServerDAO(ip_adress, port, id));
            });
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        // vérifie que les argument du main existe dans le fichier structure.txt
        ServerDAO own_DAO = null;
        for (ServerDAO serverDAO : serverList) {
            if(own_ip_adress.equals(serverDAO.getIp_adress()) && own_port == serverDAO.getPort()) {
                own_DAO = serverDAO;
            }
        }
        if(own_DAO == null) {
            System.out.println("l'adresse ip " + own_ip_adress + " et le port " + own_port + " n'ont pas été trouvé dans le fichier " + fileName);
            exit(1);
        }
        
        // création du serveur
        DistributedServer server = null;
        try {
            server = new DistributedServer(serverList, own_DAO);
        } catch (InterruptedException ex) {
            System.out.println("impossible d'instancier le serveur");
            exit(1);
        }
        
        // création du registre pour se connecter au service Lamport
        Registry registryLamport = LocateRegistry.createRegistry(own_port);
        String url_lamport = "//" + own_ip_adress + ":" + own_port + "/" + ILamportServer.SERVICE_NAME;
        registryLamport.bind(url_lamport, server);
        System.out.println(registryLamport.list()[0]);
        
        // création du registre pour se connecter au service Global
        Registry registryGlobal = LocateRegistry.createRegistry(own_port + 3);
        registryGlobal.bind(IGlobalRMI.SERVICE_NAME, server);
        
        // connection de ce serveur à tous les autre serveurs
        /**
         * Pour se connecter nous avons décidé d'utiliser un système de timer/tentative lors de la connection
         * Le serveur va donc essayé de se connecter à un registre distant en espérant qu'il est déjà existant
         * Si la tentative échoue, il va attendre,
         * puis va réessayer plusieurs fois,
         * puis abandonnera après un certain temps.
         */
        Map<Integer, ILamportServer> serverMap = new HashMap<>();
        final int DELAY_TOSTART_ALL_SERVERS = 60;
        final int DELAY_BETWEEN_TRIES = 1000;
        Registry registry;
        for (ServerDAO serverDAO : serverList) {
            String url = "//" + serverDAO.getIp_adress() + ":" + serverDAO.getPort() + "/" + ILamportServer.SERVICE_NAME;
            if (serverDAO.getId() != own_DAO.getId()) {
                for (int i = 0; i < DELAY_TOSTART_ALL_SERVERS; i++) {
                    registry = LocateRegistry.getRegistry(url);
                    try {
                        ILamportServer serveur = (ILamportServer)registry.lookup(ILamportServer.SERVICE_NAME);
                        serverMap.put(serverDAO.getId(), serveur);
                    } catch (Exception ex) {
                        System.out.println(ex);
                        if(i == DELAY_TOSTART_ALL_SERVERS - 1) {
                            System.out.println("impossible to connect at " + url);
                            exit(1);
                        }
                        
                        // attend 1 seconde avant la prochaine tentative
                        System.out.println("impossible to connect at " + url + " next try in " + DELAY_BETWEEN_TRIES + " ms left tries " + (DELAY_TOSTART_ALL_SERVERS - i));
                        Thread.sleep(DELAY_BETWEEN_TRIES);
                    } 

                }
            }
        }
        server.setServers(serverMap);
    }
}
