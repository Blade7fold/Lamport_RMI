package lamportrmi;

/**
 * Structure des serveurs
 * 
 * @author Nathan & Jimmy
 */
public class ServerDAO {
    public final String IP_ADRESS;
    public final int PORT;
    public final int ID;

    /**
     * Constructeur de la classe ServerDAO
     * @param ip_adress Adresse IP du serveur
     * @param port Port du serveur
     * @param id ID du serveur
     */
    public ServerDAO(String ip_adress, int port, int id) {
        this.IP_ADRESS = ip_adress;
        this.PORT = port;
        this.ID = id;
    }

    /**
     * Métode pour obtenir l'adresse IP du serveur
     * @return  L'adresse IP du serveur
     */
    public String getIpAdress() {
        return IP_ADRESS;
    }

    /**
     * Métode pour obtenir le port du serveur
     * @return  Le port du serveur
     */
    public int getPort() {
        return PORT;
    }

    /**
     * Métode pour obtenir l'ID du serveur
     * @return  L'ID du serveur
     */
    public int getId() {
        return ID;
    }
}
