package sa12.group9.peer.service.thread;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.peer.service.IPeerManager;
import sa12.group9.peer.service.Kernel;

public class KeepAliveCleanupThread extends Thread
{
    private static Log log = LogFactory.getLog(KeepAliveCleanupThread.class);

    private Kernel kernel;
    private boolean running = true;
    private int keepAliveCleanupInterval;
    private int requestNewPeerThreshold;
    private IPeerManager peerManager;

    @Override
    public void run()
    {
        while (running)
        {
            try
            {
                Set<String> peerList = peerManager.getPeerSnapshot();
                for (String key : peerList)
                {
                    PeerEndpoint pe = peerManager.getPeerEndpoint(key);
                    if (pe.getLastKeepAlive() != null && pe.getLastKeepAlive().getTime() < System.currentTimeMillis() - 10000)
                    {
                        peerManager.removePeerEndpoint(pe.getAddress() + ":" + pe.getListeningPort());
                        log.debug("Removed peer " + pe.getAddress() + ":" + pe.getListeningPort());
                    }
                }
                if (peerManager.getPeerCount() < requestNewPeerThreshold)
                {
                    try
                    {
                        kernel.requestNewPeersFromServer();
                    }
                    catch (Exception e)
                    {
                        log.error("Connection to server failed");
                    }
                }
                Thread.sleep(keepAliveCleanupInterval);
            }
            catch (InterruptedException e)
            {
                log.error("Exception in KeepAliveCleanupThread\n" + e.getMessage());
            }
        }
    }

    public void setKernel(Kernel kernel)
    {
        this.kernel = kernel;
    }

    public void setRequestNewPeerThreshold(int requestNewPeerThreshold)
    {
        this.requestNewPeerThreshold = requestNewPeerThreshold;
    }

    public void setKeepAliveCleanupInterval(int keepAliveCleanupInterval)
    {
        this.keepAliveCleanupInterval = keepAliveCleanupInterval;
    }

    public void setPeerManager(IPeerManager peerManager)
    {
        this.peerManager = peerManager;
    }

    public void shutdown()
    {
        log.debug("Shutdown KeepAliveCleanupThread");
        running = false;
    }
}
