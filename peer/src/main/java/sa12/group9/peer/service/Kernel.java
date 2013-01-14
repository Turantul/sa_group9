package sa12.group9.peer.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.media.IFingerprintService;
import sa12.group9.common.util.Constants;
import sa12.group9.peer.service.thread.AliveThread;
import sa12.group9.peer.service.thread.KeepAliveCleanupThread;
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
    private KeepAliveCleanupThread keepAliveCleanup;
    
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

                keepAliveOutgoing.setKernel(this);
                keepAliveOutgoing.start();

                keepAliveIncoming.setKernel(this);
                keepAliveIncoming.start();
                
                management.setKernel(this);
                management.start();

                keepAliveCleanup = new KeepAliveCleanupThread(this);
                keepAliveCleanup.start();
                
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

        boolean running = true;
        InputStreamReader cin = new InputStreamReader(System.in);
		BufferedReader bin = new BufferedReader(cin);
		String in;
		
		
        while (running){
        	try {
    			in = bin.readLine();
    			//System.out.println(in);
    			if(in.equals("!exit")){
    				System.out.println("Exiting the Peer program");
    				running = false;
    				keepAliveIncoming.shutdown();
    				keepAliveOutgoing.shutdown();
    				management.shutdown();
    				pool.shutdown();
    			}
    			if(in.equals("!peers")){
    				System.out.println("Current known peers:");
    				List<PeerEndpoint> peerList = getPeerSnapshot();
    				for(PeerEndpoint pe : peerList){
    					System.out.println(pe.getAddress()+":"+pe.getListeningPort()+":"+pe.getKeepAlivePort()+" - "+pe.getLastKeepAlive());
    				}
    			}
    			if(in.startsWith("!files")){
    				System.out.println("Current media files:");
    				List<Fingerprint> fingerprintList = getFingerprintSnapshot();
    				for(Fingerprint fp : fingerprintList){
    					System.out.println(fp.toString());
    				}
    			}
    			if(in.startsWith("!addpeer")){
    				String[] split = in.split(" ");
    				if(split.length!=4){
    					System.out.println("Correct usage is !addpeer <address> <listeningport> <keepaliveport>");
    				}else{
    					PeerEndpoint peer = new PeerEndpoint();
    					peer.setAddress(split[1].trim());
    					peer.setListeningPort(Integer.parseInt(split[2].trim()));
    					peer.setKeepAlivePort(Integer.parseInt(split[3].trim()));
    					peer.setLastKeepAlive(new Date(System.currentTimeMillis()));
    					addPeerEndpoint(peer);
    					System.out.println("New peer has been added");
    				}
    			}
    			if(in.startsWith("!addfile")){
    				String[] split = in.split(" ");
    				if(split.length!=2){
    					System.out.println("Correct usage is !addfile <location>");
    				}else{
    					ApplicationContext ctx = new ClassPathXmlApplicationContext(Constants.SPRINGBEANS);
    					IFingerprintService fingerprintService = (IFingerprintService) ctx.getBean("fingerprintService");
    					Fingerprint finger = fingerprintService.generateFingerprint(split[1].trim());
    					addFingerprint(finger);
    					ByteArrayOutputStream bos = new ByteArrayOutputStream();
    					ObjectOutput out = null;
    					try {
    					  out = new ObjectOutputStream(bos);   
    					  out.writeObject(finger);
    					  byte[] yourBytes = bos.toByteArray();
    					  System.out.println("Fingerprint length: "+yourBytes.length);
    					}catch(Exception e){};
    					System.out.println("New file has been added");
    				}
    			}
    		} catch (IOException e) {
    			System.out.println("Socket to Proxy has been closed. Press <ENTER> to shutdown Client");
    			//e.printStackTrace();
    		}
        }
        
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
    		for(PeerEndpoint pe: peerList){
    			if(pe.getAddress().equals(peer.getAddress())&&pe.getKeepAlivePort()==peer.getKeepAlivePort()&&pe.getListeningPort()==peer.getListeningPort())
    			{
    				peerList.remove(pe);
    			}
    		}
    		peerList.add(peer);
    	}
    }
    
    public List<PeerEndpoint> getPeerSnapshot(){
    	synchronized(peerList){
    		List<PeerEndpoint> ret = new ArrayList<PeerEndpoint>(peerList);
    		return ret;
    	}
    }
    
    public List<Fingerprint> getFingerprintSnapshot(){
    	synchronized(fingerprintList){
    		List<Fingerprint> ret = new ArrayList<Fingerprint>(fingerprintList);
    		return ret;
    	}
    }
    
    public void removePeerEndpoint(PeerEndpoint pe) {
		synchronized(peerList){
			peerList.remove(pe);
		}
	}
    
}

	
