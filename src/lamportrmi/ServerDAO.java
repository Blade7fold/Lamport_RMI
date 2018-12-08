package lamportrmi;

/**
 *
 * @author jimmy
 */
public class ServerDAO {
    public final String ip_adress;
    public final int port;
    public final int id;

    public ServerDAO(String ip_adress, int port, int id) {
        this.ip_adress = ip_adress;
        this.port = port;
        this.id = id;
    }

    public String getIp_adress() {
        return ip_adress;
    }

    public int getPort() {
        return port;
    }

    public int getId() {
        return id;
    }
}
