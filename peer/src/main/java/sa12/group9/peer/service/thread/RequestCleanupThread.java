package sa12.group9.peer.service.thread;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.peer.service.IRequestManager;

public class RequestCleanupThread extends Thread
{
    private static Log log = LogFactory.getLog(RequestCleanupThread.class);

    private boolean running = true;
    private int keepAliveCleanupInterval;
    private IRequestManager requestManager;

    @Override
    public void run()
    {
        while (running)
        {
            try
            {
                Set<String> requestList = requestManager.getRequestSnapshot();
                for (String id : requestList)
                {
                    Long time = requestManager.getRequestTime(id);
                    if (time < System.currentTimeMillis())
                    {
                        requestManager.removeRequest(id);
                        log.debug("Removed request " + id);
                    }
                }
                Thread.sleep(keepAliveCleanupInterval);
            }
            catch (InterruptedException e)
            {
                log.error("Exception in RequestCleanupThread\n" + e.getMessage());
            }
        }
    }

    public void setKeepAliveCleanupInterval(int keepAliveCleanupInterval)
    {
        this.keepAliveCleanupInterval = keepAliveCleanupInterval;
    }

    public void shutdown()
    {
        log.debug("Shutdown RequestCleanupThread");
        running = false;
    }

    public void setRequestManager(IRequestManager requestManager)
    {
        this.requestManager = requestManager;
    }
}
