package sa12.group9.server.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.IsAliveNotification;
import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerList;
import sa12.group9.server.handler.IPeerServiceHandler;
import sa12.group9.server.handler.PeerServiceHandler;
import sa12.group9.server.thread.KeepAliveCleanupThread;
import sa12.group9.server.util.PropertiesHelper;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/peer")
public class PeerService
{
    private static Log log = LogFactory.getLog(PeerService.class);
    private IPeerServiceHandler peerHandler = new PeerServiceHandler();
    private KeepAliveCleanupThread aliveCleanupThread;

    public PeerService()
    {
        aliveCleanupThread = new KeepAliveCleanupThread();

        try
        {
            int cleanupPeriod = Integer.parseInt(PropertiesHelper.getProperty("alive.cleanupPeriod"));
            aliveCleanupThread.setCleanupPeriod(cleanupPeriod);
            aliveCleanupThread.start();
        }
        catch (IOException e)
        {
            log.error("Error reading configuration properties.");
        }
    }

    @POST
    @Path("getNeighbors")
    @Consumes("application/json")
    @Produces("application/json")
    public PeerList getNeighbors(LoginRequest request)
    {
        log.info("Got neigbor request for " + request.getUsername());

        return peerHandler.getRandomPeerList(request);
    }

    @POST
    @Path("isAlive")
    @Consumes("application/json")
    public boolean isAlive(IsAliveNotification notification, @Context HttpServletRequest hsr)
    {
        log.debug("Got isAlive from " + notification.getUsername());

        return peerHandler.markAsAlive(notification, hsr.getRemoteAddr());
    }
}
