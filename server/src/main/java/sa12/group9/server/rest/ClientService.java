package sa12.group9.server.rest;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.SearchRequest;
import sa12.group9.common.beans.SearchResponse;

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
    public SearchResponse newSearch(SearchRequest request)
    {
        //TODO: check coins and log

        SearchResponse response = new SearchResponse();
        response.setErrorMsg("No peers available!");
        return response;
    }
}
