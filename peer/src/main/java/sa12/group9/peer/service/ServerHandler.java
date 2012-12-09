package sa12.group9.peer.service;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.MediaType;

import sa12.group9.common.beans.IsAliveNotification;
import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.PeerList;

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
    
    public List<PeerEndpoint> getNeighbors(String username, String password)
    {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        WebResource resource = client.resource(serverUrl + "getNeighbors");
        PeerList response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(PeerList.class, request);
        
        List<PeerEndpoint> peers = Collections.synchronizedList(response.getPeers());

        return peers;
    }
    
    @Override
    public void isAlive(String username, String password, int listeningPort, int keepAlivePort)
    {
        IsAliveNotification request = new IsAliveNotification();
        request.setUsername(username);
        request.setPassword(password);
        request.setListeningPort(listeningPort);
        request.setKeepAlivePort(keepAlivePort);

        WebResource resource = client.resource(serverUrl + "isAlive");
        resource.type(MediaType.APPLICATION_JSON).post(request);
    }
    
    public void setServerUrl(String serverUrl)
    {
        this.serverUrl = serverUrl;
    }
}
