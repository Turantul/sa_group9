package sa12.group9.client.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.core.MediaType;

import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.SearchRequest;
import sa12.group9.common.beans.SearchResponse;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class ServiceProvider
{
    public static Fingerprint generateFingerprint(String path) throws IOException
    {
        FingerprintSystem system = new FingerprintSystem(44100);
        return system.fingerprint(Files.readAllBytes(Paths.get(path)));
    }
    
    public static String loginAtServer(String username, String password)
    {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        
        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        
        Client client = Client.create(config);
        WebResource resource = client.resource("http://localhost:8080/swazam-server-1.0-SNAPSHOT/client/login");
        String response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(String.class, request);
        
        return response;
    }

    public static SearchResponse generateSearchRequest(String userId, int hash)
    {
        SearchRequest request = new SearchRequest();
        request.setUserId(userId);
        request.setHash(hash);
        
        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        
        Client client = Client.create(config);
        WebResource resource = client.resource("http://localhost:8080/swazam-server-1.0-SNAPSHOT/client/issueSearchRequest");
        SearchResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(SearchResponse.class, request);
        
        return response;
    }
}
