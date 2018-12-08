package lamportrmi;

import static java.lang.System.exit;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Class permettant de se connecter serveur IGlobalRMI
 * 
 * Le main proposant une interface ligne de commande à l'utilisateur 
 * pour modifier/afficher la variable global que le serveur gère.
 */
public class ClientOfGlobalRMIServer {
    
    IGlobalRMI serveur;
    final static int GET_VAR = 1;
    final static int SET_VAR = 2;
    final static int QUIT = -1;

    public ClientOfGlobalRMIServer(String ipAdress, int port) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ipAdress, port);
        serveur = (IGlobalRMI)registry.lookup("GLOBAL_RMI");
    }
    
    /**
     * appel RMI pour récupérer la valeur de la variable global du serveur distribué
     * 
     * @return la valeur de la variable global du serveur distribué
     */
    public int getVariable() {
        try {
            return serveur.getVariable();
        } catch (RemoteException ex) {
            System.out.println("soucis réseau");
        }
        return -1;
    }
    
    /**
     * appel RMI pour modifier la variable global du serveur distribué.
     * @param newValue 
     */
    public void setVariable(int newValue) {
        try {
            serveur.setVariable(newValue);
        } catch (RemoteException ex) {
            System.out.println("soucis réseau");
        }
    }
    
    public static void main (String[] args){
        // se connect au serveur RMI distant
        //String ip_adress = args[0];
        //int port = Integer.parseInt(args[1]);
        
        /*******************FOR DEBUG*********************/
        String ip_adress = "127.0.0.1";
        int port = 1102;
        //int port = 1103;
        //int port = 1104;
        /********************FOR DEBUG********************/
        ClientOfGlobalRMIServer client =  null;
        try {
            client = new ClientOfGlobalRMIServer(ip_adress, port);
        } catch (RemoteException ex ) {
            System.out.println("impossible to get the registry at adress " + ip_adress + " and port " + port);
            exit(1);
        } catch (NotBoundException ex) {
            System.out.println("lookup failed");
            exit(1);
        }
        
        // demande à l'utilisateur les actions qu'il souhaite effectuer
        Scanner in = new Scanner(System.in);
        int num;
        do {
            System.out.println("================================");
            System.out.println(GET_VAR + ": read the global variable");
            System.out.println(SET_VAR + ": write the global variable");
            num = in.nextInt();
            switch(num) {
                case GET_VAR:
                    System.out.println("The current value is " + client.getVariable());
                    break;
                case SET_VAR:
                    System.out.print("What is the new value : ");
                    num = in.nextInt();
                    client.setVariable(num);
                    break;
            }
        } while(num != QUIT);
    }
}
