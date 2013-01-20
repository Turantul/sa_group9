package sa12.group9.server.rest;

import java.io.IOException;
import java.util.Properties;

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
import sa12.group9.server.thread.KeepAliveCleanupThread;
import sa12.group9.server.thread.RequestCleanupThread;


import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/client")
public class ClientService
{
    private static Log log = LogFactory.getLog(ClientService.class);
    private IClientServiceHandler clientHandler = new ClientServiceHandler();
    private RequestCleanupThread requestCleanupThread;

    public ClientService(){
    	this.requestCleanupThread = new RequestCleanupThread();
		Properties prop = new Properties();
        try {
			prop.load(ClientServiceHandler.class.getClassLoader().getResourceAsStream("config.properties"));
			int cleanupPeriod = Integer.parseInt(prop.getProperty("request.cleanupPeriod"));
			this.requestCleanupThread.setCleanupPeriod(cleanupPeriod);
			this.requestCleanupThread.start();
		} catch (IOException e) {
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
        
        SearchIssueResponse response = clientHandler.issueSearchRequest(request);

        return response;
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
