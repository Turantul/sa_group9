package sa12.group9.client.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.core.MediaType;

import sa12.group9.client.gui.action.MainAction;
import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.SearchIssueResponse;
import sa12.group9.common.beans.FoundNotification;
import sa12.group9.common.beans.SuccessRequest;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class ServiceProvider
{
    public static String serverUrl = "http://localhost:8080/swazam-server-1.0-SNAPSHOT/client/";
    
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
        WebResource resource = client.resource(serverUrl + "login");
        String response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(String.class, request);
        
        return response;
    }

    public static SearchIssueResponse generateSearchRequest(String userId, int hash)
    {
        SearchIssueRequest request = new SearchIssueRequest();
        request.setUserId(userId);
        request.setHash(hash);
        
        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        
        Client client = Client.create(config);
        WebResource resource = client.resource(serverUrl + "issueSearchRequest");
        SearchIssueResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(SearchIssueResponse.class, request);
        
        return response;
    }
    
    public static void notifySuccess(String userId, int hash, String peerUsername)
    {
        SuccessRequest request = new SuccessRequest();
        request.setUserId(userId);
        request.setHash(hash);
        request.setPeerUsername(peerUsername);
        
        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        
        Client client = Client.create(config);
        WebResource resource = client.resource(serverUrl + "notifySuccess");
        resource.type(MediaType.APPLICATION_JSON).post(request);
    }

    public static void openListeningSocket(final MainAction mainAction) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(123456);
        
        long start = System.currentTimeMillis();
        do
        {
            try
            {
                Socket socket = serverSocket.accept();
                
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        //TODO: process response
                
                        FoundNotification information = new FoundNotification();
                        mainAction.receivingCallback(information);
                    }
                }.start();
                
                socket.close();
            }
            catch (IOException e)
            {
                if (!serverSocket.isClosed())
                {
                    System.out.println("Error reading from TCP socket!");
                }
            }
        }
        while (System.currentTimeMillis() - start < 20000);
        
        serverSocket.close();
    }

    public static void sendSearchRequest(PeerEndpoint peer, Fingerprint finger) throws IOException
    {
        Socket socket = new Socket(peer.getAddress(), peer.getPort());

        //TODO: send out request with fingerprint, client callback address and ttl

        socket.close();
    }
}
