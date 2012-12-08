package sa12.group9.client.service;

import javax.ws.rs.core.MediaType;

import sa12.group9.common.beans.FoundInformation;
import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.SearchIssueResponse;
import sa12.group9.common.beans.SuccessRequest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class ServerHandler implements IServerHandler
{
    private String serverUrl;

    private Client client;

    public ServerHandler()
    {
        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);

        client = Client.create(config);
    }

    public boolean loginAtServer(String username, String password)
    {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        WebResource resource = client.resource(serverUrl + "login");
        boolean response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(Boolean.class, request);

        return response;
    }

    public SearchIssueResponse generateSearchRequest(String username, String password, String id, int hash)
    {
        SearchIssueRequest request = new SearchIssueRequest();
        request.setId(id);
        request.setHash(hash);
        request.setUsername(username);
        request.setPassword(password);

        WebResource resource = client.resource(serverUrl + "issueSearchRequest");
        SearchIssueResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(SearchIssueResponse.class, request);

        return response;
    }

    public void notifySuccess(String username, String password, String id, FoundInformation information)
    {
        SuccessRequest request = new SuccessRequest();
        request.setId(id);
        request.setInformation(information);
        request.setUsername(username);
        request.setPassword(password);

        WebResource resource = client.resource(serverUrl + "notifySuccess");
        resource.type(MediaType.APPLICATION_JSON).post(request);
    }

    public void setServerUrl(String serverUrl)
    {
        this.serverUrl = serverUrl;
    }
}
