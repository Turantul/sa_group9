package sa12.group9.server.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.SearchIssueResponse;
import sa12.group9.common.beans.SuccessRequest;
import sa12.group9.server.handlers.IClientServiceHandler;
import sa12.group9.server.handlers.IPeerServiceHandler;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/client")
public class ClientService
{
    private static Log log = LogFactory.getLog(ClientService.class);
    private IClientServiceHandler clientHandler;

    @POST
    @Path("login")
    @Consumes("application/json")
    public boolean login(LoginRequest request)
    {
        log.info("Got login request for " + request.getUsername());

        return clientHandler.verifyLogin(request.getUsername(), request.getPassword());

    }

    @POST
    @Path("issueSearchRequest")
    @Consumes("application/json")
    @Produces("application/json")
    public SearchIssueResponse issueSearchRequest(SearchIssueRequest request)
    {
        log.info("Got search issue request for " + request.getUsername());

        // TODO: check coins and log
        // also provide reasonable TTL, seconds to wait and amount of peers for forwarding!

        SearchIssueResponse response = new SearchIssueResponse();
        response.setErrorMsg("No peers available!");
        return response;
    }

    @POST
    @Path("notifySuccess")
    @Consumes("application/json")
    public void notifySuccess(SuccessRequest request)
    {
        log.info("Got success notification from " + request.getUsername());

        
        
        // TODO: update coins (peer and client) and log
    }
}
