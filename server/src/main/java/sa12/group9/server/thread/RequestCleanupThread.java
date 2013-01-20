package sa12.group9.server.thread;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.server.dao.IPeerDAO;
import sa12.group9.server.dao.IRequestDAO;
import sa12.group9.server.dao.MongoPeerDAO;
import sa12.group9.server.dao.MongoRequestDAO;

public class RequestCleanupThread extends Thread
{
	private static Log log = LogFactory.getLog(RequestCleanupThread.class);
	
    private boolean running=true;
    private int cleanupPeriod;
    private IRequestDAO requestdao = MongoRequestDAO.getInstance();
    
    public RequestCleanupThread(){
    }
    
    @Override
    public void run()
    {
    	while(running){
	    	try {
	    		requestdao.cleanupRequests();
	    		Thread.sleep(cleanupPeriod);				
			} catch (InterruptedException e) {
				log.error("Exception in RequestCleanupThread\n"+e.getMessage());
			}
    	}
    }

	public void setCleanupPeriod(int cleanupPeriod) {
		this.cleanupPeriod = cleanupPeriod;
	}

	public void shutdown(){
		log.debug("Shutdown RequestCleanupThread");
    	running = false;
    }
}
