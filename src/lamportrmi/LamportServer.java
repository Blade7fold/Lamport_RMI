package lamportrmi;

import java.util.List;

/**
 *
 * @author Nathan
 */
public class LamportServer implements ILamportServer {
    
    List<ILamportServer> serverLamportManaged;
    
    public LamportServer(List<ILamportServer> serverLamportManaged) {
        this.serverLamportManaged = serverLamportManaged;
    }
    
    public void askLock() {
        
    }
    
    public void finishLock() {
        
    }

    @Override
    public void requestAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void responseAll(String ipAdress, int port) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void freeSC() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
