package lamportrmi;

import java.util.List;

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

    public DistributedServer(List<ILamportServer> serverLamportManaged) {
        super(serverLamportManaged);
    }
    
    
    
    public int getVariable() {
        return globalVariable;
    }
    
    public void setVariable(int newValue) {
        this.globalVariable = newValue;
    }
}
