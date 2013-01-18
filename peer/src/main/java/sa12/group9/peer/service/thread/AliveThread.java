package sa12.group9.peer.service.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.peer.service.Kernel;

public abstract class AliveThread extends Thread
{
    protected Kernel kernel;
    
    public void setKernel(Kernel kernel)
    {
        this.kernel = kernel;
    }
    
    public abstract void shutdown();
}
