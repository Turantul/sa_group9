package sa12.group9.peer.service;

import com.sun.jersey.api.client.Client;
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
    
    public void setServerUrl(String serverUrl)
    {
        this.serverUrl = serverUrl;
    }
}
