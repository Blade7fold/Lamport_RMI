package lamportrmi;

/**
 *
 * @author Nathan
 */
public interface ILamportServer {
    final String IP_ADRESS = "0.0.0.0";
    final int PORT = 1234;
    
    public void requestAll();
    public void responseAll(String ipAdress, int port);
    public void freeSC();
}
