package sa12.group9.server.rest;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.SearchIssueResponse;
import sa12.group9.common.beans.SuccessRequest;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/client")
public class ClientService
{
    @POST
    @Path("login")
    @Consumes("application/json")
    @Produces("application/json")
    public UUID login(LoginRequest request)
    {
        //TODO: check and log
        
        return UUID.randomUUID();
    }
    
    @POST
    @Path("issueSearchRequest")
    @Consumes("application/json")
    @Produces("application/json")
    public SearchIssueResponse issueSearchRequest(SearchIssueRequest request)
    {
        //TODO: check coins and log

        SearchIssueResponse response = new SearchIssueResponse();
        response.setErrorMsg("No peers available!");
        return response;
    }
    
    @POST
    @Path("notifySuccess")
    @Consumes("application/json")
    public void notifySuccess(SuccessRequest request)
    {
        //TODO: update coins and log
    }
}
