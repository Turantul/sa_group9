package sa12.group9.server.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerList;
import sa12.group9.common.beans.IsAliveNotification;
import sa12.group9.server.handler.IPeerServiceHandler;
import sa12.group9.server.handler.PeerServiceHandler;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/peer")
public class PeerService
{
	private static Log log = LogFactory.getLog(PeerService.class);
	private IPeerServiceHandler peerHandler = new PeerServiceHandler();

	@POST
	@Path("login")
	@Consumes("application/json")
	public boolean login(IsAliveNotification request)
	{
		log.info("Got login request for " + request.getUsername());

		return peerHandler.verifyLogin(request);
	}

	@POST
	@Path("getNeighbors")
	@Consumes("application/json")
	@Produces("application/json")
	public PeerList getNeighbors(LoginRequest request)
	{
		log.info("Got neigbor request for " + request.getUsername());

		return peerHandler.getRandomPeerList(10);
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
