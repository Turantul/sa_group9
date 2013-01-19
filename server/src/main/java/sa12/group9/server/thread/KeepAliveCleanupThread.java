package sa12.group9.server.thread;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.server.dao.IPeerDAO;
import sa12.group9.server.dao.MongoPeerDAO;

public class KeepAliveCleanupThread extends Thread
{
	private static Log log = LogFactory.getLog(KeepAliveCleanupThread.class);
	
    private boolean running=true;
    private int cleanupPeriod;
    private IPeerDAO peerdao = MongoPeerDAO.getInstance();
    
    public KeepAliveCleanupThread(){
    }
    
    @Override
    public void run()
    {
    	while(running){
	    	try {
	    		peerdao.cleanupPeers(cleanupPeriod);
	    		Thread.sleep(cleanupPeriod);				
			} catch (InterruptedException e) {
				log.error("Exception in KeepAliveCleanupThread\n"+e.getMessage());
			}
    	}
    }

	public void setCleanupPeriod(int cleanupPeriod) {
		this.cleanupPeriod = cleanupPeriod;
	}

	public void shutdown(){
		log.debug("Shutdown KeepAliveCleanupThread");
    	running = false;
    }
}
