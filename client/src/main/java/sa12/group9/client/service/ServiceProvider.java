package sa12.group9.client.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.core.MediaType;

import sa12.group9.client.gui.action.MainAction;
import sa12.group9.common.beans.FoundInformation;
import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.SearchIssueResponse;
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
    private String serverUrl;
    private float herz;
    private int listeningPort;

    public Fingerprint generateFingerprint(String path) throws IOException
    {
        FingerprintSystem system = new FingerprintSystem(herz);
        return system.fingerprint(Files.readAllBytes(Paths.get(path)));
    }

    public String loginAtServer(String username, String password)
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

    public SearchIssueResponse generateSearchRequest(String userId, int hash)
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

    public void notifySuccess(String userId, int hash, FoundInformation information)
    {
        SuccessRequest request = new SuccessRequest();
        request.setUserId(userId);
        request.setHash(hash);
        request.setInformation(information);

        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);

        Client client = Client.create(config);
        WebResource resource = client.resource(serverUrl + "notifySuccess");
        resource.type(MediaType.APPLICATION_JSON).post(request);
    }

    public void openListeningSocket(final MainAction mainAction, int seconds) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(listeningPort);

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
                        // TODO: process response

                        FoundInformation information = new FoundInformation();
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
        } while (System.currentTimeMillis() - start < seconds * 1000);

        serverSocket.close();
    }

    public void sendSearchRequest(PeerEndpoint peer, Fingerprint finger) throws IOException
    {
        Socket socket = new Socket(peer.getAddress(), peer.getPort());

        // TODO: send out request with fingerprint, client callback address and
        // ttl

        socket.close();
    }

    public void setServerUrl(String serverUrl)
    {
        this.serverUrl = serverUrl;
    }

    public void setHerz(float herz)
    {
        this.herz = herz;
    }

    public void setListeningPort(int listeningPort)
    {
        this.listeningPort = listeningPort;
    }
}
