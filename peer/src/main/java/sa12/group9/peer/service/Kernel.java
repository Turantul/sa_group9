package sa12.group9.peer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.peer.service.thread.AliveThread;
import sa12.group9.peer.service.thread.ManagementThread;
import sa12.group9.peer.service.thread.RequestHandler;

public class Kernel
{
    private static Log log = LogFactory.getLog(Kernel.class);

    private IServerHandler serverHandler;

    private String username;
    private String password;
    private int listeningPort;

    private AliveThread keepAliveOutgoing;
    private AliveThread keepAliveIncoming;
    private ManagementThread management;
    private ExecutorService pool;
    ServerSocket serverSocket;
    
    private List<Fingerprint> fingerprintList;
    private List<PeerEndpoint> peerList;

    @SuppressWarnings("unused")
    private void initialize()
    {
        log.info("Starting up");
        fingerprintList = Collections.synchronizedList(new ArrayList<Fingerprint>());
        try
        {
            boolean success = serverHandler.loginAtServer(username, password);

            if (success)
            {
                List<PeerEndpoint> peers = serverHandler.getNeighbors(username, password);
                peerList = Collections.synchronizedList(peers);

                keepAliveOutgoing.setPeers(peers);
                keepAliveOutgoing.start();

                keepAliveIncoming.setPeers(peers);
                keepAliveIncoming.start();
                
                management.setKernel(this);
                management.start();

                handleRequests();
            }
            else
            {
                System.out.println("Invalid username/password combination!");
            }
        }
        catch (Exception e)
        {
            System.out.println("The server could not be reached.\nPlease try again later.");
        }
    }

    private void handleRequests()
    {
        log.info("Peer successfully started\nListening for requests...");
        pool = Executors.newCachedThreadPool();

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    serverSocket = new ServerSocket(listeningPort);
                    while (!serverSocket.isClosed())
                    {
                        try
                        {
                            Socket socket = serverSocket.accept();
                            pool.execute(new RequestHandler(socket));
                        }
                        catch (IOException e)
                        {
                            if (!serverSocket.isClosed())
                            {
                                System.out.println("Error reading from TCP socket!");
                            }
                        }
                    }
                }
                catch (IOException e)
                {
                    System.out.println("Error opening TCP socket!");
                }
            }
        }.start();

        try
        {
            BufferedReader brc = new BufferedReader(new InputStreamReader(System.in));
            brc.readLine();
        }
        catch (IOException e)
        {
            System.out.println("Error reading from console!");
        }

        log.info("Shutting down");
        
        pool.shutdown();
        keepAliveIncoming.interrupt();
        keepAliveOutgoing.interrupt();
        management.interrupt();
        
        if (serverSocket != null)
        {
            try
            {
                serverSocket.close();
            }
            catch (IOException e)
            {
                System.out.println("Error closing listening socket!");
            }
        }
    }

    public void setServerHandler(IServerHandler serverHandler)
    {
        this.serverHandler = serverHandler;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setListeningPort(int listeningPort)
    {
        this.listeningPort = listeningPort;
    }

    public void setKeepAliveOutgoing(AliveThread keepAliveOutgoing)
    {
        this.keepAliveOutgoing = keepAliveOutgoing;
    }

    public void setKeepAliveIncoming(AliveThread keepAliveIncoming)
    {
        this.keepAliveIncoming = keepAliveIncoming;
    }

    public void setManagement(ManagementThread management)
    {
        this.management = management;
    }
    
    public void addFingerprint(Fingerprint fingerprint){
    	System.out.println("Adding Fingerprint: "+fingerprint.getShiftDuration());
    	synchronized(fingerprintList){
    		fingerprintList.add(fingerprint);
    	}
    }
    
    public void addPeerEndpoint(PeerEndpoint peer){
    	synchronized(peerList){
    		peerList.add(peer);
    	}
    }
}
