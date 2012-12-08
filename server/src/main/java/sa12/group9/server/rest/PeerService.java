package sa12.group9.server.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerList;
import sa12.group9.common.beans.SearchIssueRequest;

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

        // TODO: check and log

        return true;
    }

    @POST
    @Path("getNeighbors")
    @Consumes("application/json")
    @Produces("application/json")
    public PeerList getNeighbors(SearchIssueRequest request)
    {
        log.info("Got neigbor request for " + request.getUsername());

        // TODO: select neighbors

        PeerList response = new PeerList();
        return response;
    }
}
