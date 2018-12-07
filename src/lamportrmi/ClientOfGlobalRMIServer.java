package lamportrmi;

/**
 *
 * @author Nathan
 */
public class ClientOfGlobalRMIServer {
    
    private final String IP_ADRESS;
    private final int PORT;

    public ClientOfGlobalRMIServer(String ipAdress, int port) {
        this.IP_ADRESS = ipAdress;
        this.PORT = port;
    }
    
    public int getVariable() {
        return 0;
    }
    
    public void setVariable(int newValue) {
        
    }
}
