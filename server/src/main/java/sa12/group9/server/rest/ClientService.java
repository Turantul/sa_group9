package sa12.group9.server.rest;

import java.io.IOException;

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
import sa12.group9.server.handler.ClientServiceHandler;
import sa12.group9.server.handler.IClientServiceHandler;
import sa12.group9.server.thread.RequestCleanupThread;
import sa12.group9.server.util.PropertiesHelper;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/client")
public class ClientService
{
    private static Log log = LogFactory.getLog(ClientService.class);
    private IClientServiceHandler clientHandler = new ClientServiceHandler();
    private RequestCleanupThread requestCleanupThread;

    public ClientService()
    {
        requestCleanupThread = new RequestCleanupThread();

        try
        {
            int cleanupPeriod = Integer.parseInt(PropertiesHelper.getProperty("request.cleanupPeriod"));
            requestCleanupThread.setCleanupPeriod(cleanupPeriod);
            requestCleanupThread.start();
        }
        catch (IOException e)
        {
            log.error("Error reading configuration properties.");
        }
    }

    @POST
    @Path("login")
    @Consumes("application/json")
    public boolean login(LoginRequest request)
    {
        log.info("Got login request for " + request.getUsername());

        return clientHandler.verifyLogin(request);
    }

    @POST
    @Path("issueSearchRequest")
    @Consumes("application/json")
    @Produces("application/json")
    public SearchIssueResponse issueSearchRequest(SearchIssueRequest request)
    {
        log.info("Got search issue request for " + request.getUsername());

        return clientHandler.issueSearchRequest(request);
    }

    @POST
    @Path("notifySuccess")
    @Consumes("application/json")
    public void notifySuccess(SuccessRequest request)
    {
        log.info("Got success notification from " + request.getUsername());

        clientHandler.notifySuccess(request);
    }
}
