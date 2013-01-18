package sa12.group9.server.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerList;
import sa12.group9.common.beans.IsAliveNotification;
import sa12.group9.server.handlers.ClientServiceHandler;
import sa12.group9.server.handlers.IClientServiceHandler;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/peer")
public class PeerService
{
    private static Log log = LogFactory.getLog(PeerService.class);

    @POST
    @Path("login")
    @Consumes("application/json")
    public boolean login(LoginRequest request)
    {
        log.info("Got login request for " + request.getUsername());
        
        IClientServiceHandler serviceHandler = new ClientServiceHandler();
        
        return serviceHandler.verifyLogin(request.getUsername(), request.getPassword());
        
        // TODO: check

        //return true;
    }

    @POST
    @Path("getNeighbors")
    @Consumes("application/json")
    @Produces("application/json")
    public PeerList getNeighbors(LoginRequest request)
    {
        log.info("Got neigbor request for " + request.getUsername());

        IClientServiceHandler serviceHandler = new ClientServiceHandler();
        
        System.out.println(serviceHandler.getRandomPeerList(10));
        
        return serviceHandler.getRandomPeerList(10);

        //PeerList response = new PeerList();
        //return response;
    }
    
    @POST
    @Path("isAlive")
    @Consumes("application/json")
    public void isAlive(IsAliveNotification notification)
    {
        log.debug("Got isAlive from " + notification.getUsername());

        // TODO: mark as alive
    }
}
