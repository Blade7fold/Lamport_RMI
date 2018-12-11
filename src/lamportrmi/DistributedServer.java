package lamportrmi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.lang.System.exit;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Classe principale pour les serveurs. Ici se lancent les différents serveurs 
 * interconnectés possédant tous la même variable globale modifiable
 * 
 * @author Nathan & Jimmy
 */
public class DistributedServer extends LamportServer implements IGlobalRMI {
    private int globalVariable; // Variable que l'on voudra modifier

    /**
     * Constructeur de la classe DistributedServer
     * @param servers Liste d'informations concernant tous les serveurs du pool
     * auquel on est connecté
     * @param ownDAO Information concernant le serveur du site tournant sur
     * ce processus
     * @throws RemoteException
     * @throws InterruptedException 
     */
    public DistributedServer(List<ServerDAO> servers, ServerDAO ownDAO)
            throws RemoteException, InterruptedException {
        super(servers, ownDAO);
    }
    
    @Override
    public int getVariable() throws RemoteException {
        return globalVariable;
    }
    
    @Override
    public void setVariable(int newValue) throws RemoteException {
        askLock();      // Demande de la section critique
        
        // Change la variable pour nous-même
        System.out.println("Value before change = " + this.globalVariable);
        this.setVariableGlobally(newValue);
        
        // Change la variable pour les autres serveurs
        for (Map.Entry<Integer, ILamportServer> entry : serverMap.entrySet()) {
            Integer id = entry.getKey();
            ILamportServer server = entry.getValue();
            if (ID != id) {
                server.setVariableGlobally(newValue);
            }
        }
        
        finishLock();   // Libération de la section critique
    }

    @Override
    public void setVariableGlobally(int newValue) throws RemoteException {
        this.globalVariable = newValue;
        System.out.println("Value after change = " + this.globalVariable);
    }
    
    public static void main (String[] args)
            throws RemoteException,
            AlreadyBoundException,
            InterruptedException,
            FileNotFoundException,
            UnsupportedEncodingException {
        String own_ip_adress = args[0];             // Adresse IP du serveur
        int own_port = Integer.parseInt(args[1]);   // Port du serveur
        
        // Lecture de la topologie et création d'une liste lui correspondant
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
        
        // Vérifie que les argument du main existe dans le fichier structure.txt
        ServerDAO own_DAO = null;
        for (ServerDAO serverDAO : serverList) {
            if(own_ip_adress.equals(serverDAO.getIpAdress()) &&
               own_port == serverDAO.getPort()) {
                own_DAO = serverDAO;
            }
        }
        
        if(own_DAO == null) {
            System.out.println("L'adresse ip " + own_ip_adress +
                               " et le port " + own_port +
                               " n'ont pas été trouvé dans le fichier " + fileName);
            exit(1);
        }
        
        // Création du serveur
        DistributedServer server = null;
        try {
            server = new DistributedServer(serverList, own_DAO);
        } catch (InterruptedException ex) {
            System.out.println("Impossible to instanciate the server");
            exit(1);
        }
        
        // Création du registre pour se connecter au service Lamport
        Registry registryLamport = LocateRegistry.createRegistry(own_port);
        registryLamport.bind(ILamportServer.SERVICE_NAME, server);
        
        // Création du registre pour se connecter au service Global
        Registry registryGlobal = LocateRegistry.createRegistry(own_port + serverList.size());
        registryGlobal.bind(IGlobalRMI.SERVICE_NAME, server);
        
        // Connection de ce serveur à tous les autre serveurs
        /**
         * Pour se connecter nous avons décidé d'utiliser un système de 
         * timer/tentative lors de la connection.
         * Le serveur va donc essayer de se connecter à un registre distant en
         * espérant qu'il est déjà existant.
         * Si la tentative échoue, il va attendre,
         * puis va réessayer plusieurs fois,
         * puis abandonnera après un certain temps.
         */
        Map<Integer, ILamportServer> serverMap = new HashMap<>();
        final int DELAY_TO_START_ALL_SERVERS = 60;
        final int DELAY_BETWEEN_TRIES = 1000;
        Registry registry;
        for (ServerDAO serverDAO : serverList) {
            String url = serverDAO.getIpAdress() + ":" + serverDAO.getPort();
            if (serverDAO.getId() != own_DAO.getId()) {
                for (int i = 0; i < DELAY_TO_START_ALL_SERVERS; i++) {
                    registry = LocateRegistry.getRegistry(serverDAO.getIpAdress(),
                                                          serverDAO.getPort());
                    try {
                        ILamportServer serveur = (ILamportServer)registry.
                                                lookup(ILamportServer.SERVICE_NAME);
                        serverMap.put(serverDAO.getId(), serveur);
                    } catch (RemoteException | NotBoundException ex) {
                        if(i == DELAY_TO_START_ALL_SERVERS - 1) {
                            System.out.println("Impossible to connect at " + url);
                            exit(1);
                        }
                        
                        // Attend 1 seconde avant la prochaine tentative
                        System.out.println("Impossible to connect at " + url +
                                ", next try in: " + DELAY_BETWEEN_TRIES +
                                " ms; tries left: " +
                                (DELAY_TO_START_ALL_SERVERS - i));
                        Thread.sleep(DELAY_BETWEEN_TRIES);
                    } 

                }
            }
        }
        
        server.setServers(serverMap);
        System.out.println("Adresse IP: " + own_DAO.IP_ADRESS +
                           ", Port: " + own_DAO.PORT + 
                           ", ID: " + own_DAO.ID);
        System.out.println("All servers are connected!");
    }
}
