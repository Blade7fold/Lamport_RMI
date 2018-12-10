package lamportrmi;

import static java.lang.System.exit;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Classe permettant de se connecter au serveur IGlobalRMI
 * 
 * Le main propose une interface ligne de commande à l'utilisateur 
 * pour modifier/afficher la variable global que les serveurs gèrent.
 * 
 * @author Nathan & Jimmy
 */
public class ClientOfGlobalRMIServer {
    
    final static String GET_VAR = "1";  // Valeur pour afficher la variable
    final static String SET_VAR = "2";  // Valeur pour modifier la variable
    final static String QUIT = "q";     // Valeur pour quitter le programme
    
    IGlobalRMI serveur; // Serveur RMI

    /**
     * Constructeur de la classe ClientOfGlobalRMIServer
     * @param ipAdress Adresse IP du client
     * @param port Port du client
     * @throws RemoteException
     * @throws NotBoundException 
     */
    public ClientOfGlobalRMIServer(String ipAdress, int port)
            throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ipAdress, port);
        serveur = (IGlobalRMI)registry.lookup("GLOBAL_RMI");
    }
    
    /**
     * Appel RMI pour récupérer la valeur de la variable global du serveur distribué
     * @return la valeur de la variable global du serveur distribué
     */
    public int getVariable() {
        try {
            return serveur.getVariable();
        } catch (RemoteException ex) {
            System.out.println("Soucis réseau");
        }
        return -1;
    }
    
    /**
     * Appel RMI pour modifier la variable global du serveur distribué.
     * @param newValue 
     */
    public void setVariable(int newValue) {
        try {
            serveur.setVariable(newValue);
        } catch (RemoteException ex) {
            System.out.println("Soucis réseau");
        }
    }
    
    public static void main (String[] args){
        // Se connecte au serveur RMI distant
//        String ip_adress = args[0];
//        int port = Integer.parseInt(args[1]);
        
        /*******************FOR DEBUG*********************/
        String ip_adress = "127.0.0.1";
        //int port = 1102;
        int port = 1103;
        //int port = 1104;
        /********************FOR DEBUG********************/
        
        ClientOfGlobalRMIServer client =  null;
        try {
            client = new ClientOfGlobalRMIServer(ip_adress, port);
        } catch (RemoteException ex ) {
            System.out.println("Impossible to get the registry at adress "
                               + ip_adress + " and port " + port);
            exit(1);
        } catch (NotBoundException ex) {
            System.out.println("Lookup failed");
            exit(1);
        }
        
        // Demande à l'utilisateur les actions qu'il souhaite effectuer
        Scanner in = new Scanner(System.in);
        String val;
        do {
            System.out.println("================================");
            System.out.println("Select an option:");
            System.out.println(GET_VAR + ": read the global variable");
            System.out.println(SET_VAR + ": write the global variable");
            System.out.println(QUIT + ": quit the program");
            val = in.next();
            switch(val) {
                // Affichage de la valeur courrante
                case GET_VAR:
                    System.out.println("The current value is "
                                       + client.getVariable());
                    break;
                // Modification de la valeur courrante
                case SET_VAR:
                    System.out.print("What is the new number you want? ");
                    val = in.next();
                    client.setVariable(Integer.parseInt(val));
                    break;
                // Sortie du programme
                case QUIT:
                    System.out.println("Bye bye, see you.");
                    break;
                // Entrée utilisateur incorrecte
                default:
                    System.out.println("Bad entry, try again.");
                    break;
            }
        } while(!val.equals(QUIT));
    }
}
