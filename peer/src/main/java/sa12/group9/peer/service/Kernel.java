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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import sa12.group9.common.beans.P2PSearchRequest;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.media.IFingerprintService;
import sa12.group9.common.util.Constants;
import sa12.group9.peer.service.thread.AliveThread;
import sa12.group9.peer.service.thread.KeepAliveCleanupThread;
import sa12.group9.peer.service.thread.ManagementThread;
import sa12.group9.peer.service.thread.RequestHandler;
import sa12.group9.peer.service.thread.RequestThread;

public class Kernel
{
    private static Log log = LogFactory.getLog(Kernel.class);

    private IServerHandler serverHandler;

    private String username;
    private String password;
    private int listeningPort;
    private int keepAlivePort;

    private AliveThread keepAliveOutgoing;
    private AliveThread keepAliveIncoming;
    private KeepAliveCleanupThread keepAliveCleanup;
    private RequestThread requestThread;
    
    private ManagementThread management;

    private List<Fingerprint> fingerprintList;
    private Map<String, PeerEndpoint> peerMap;

    @SuppressWarnings("unused")
    private void initialize()
    {
        log.info("Starting up");
        fingerprintList = Collections.synchronizedList(new ArrayList<Fingerprint>());
        try
        {
            boolean success = sendKeepAliveToServer();

            if (success)
            {
                List<PeerEndpoint> peers = serverHandler.getNeighbors(username, password);
               
                peerMap = Collections.synchronizedMap(new HashMap<String, PeerEndpoint>());
                for(PeerEndpoint pe : peers){
                	addPeerEndpoint(pe);
                }

                keepAliveOutgoing.setKernel(this);
                keepAliveOutgoing.start();

                keepAliveIncoming.setKernel(this);
                keepAliveIncoming.start();
                
                management.setKernel(this);
                management.start();

                keepAliveCleanup.setKernel(this);
                keepAliveCleanup.start();
                
                requestThread.setKernel(this);
                requestThread.start();
                
                handleRequests();
            }
            else
            {
                log.info("Invalid username/password combination!");
            }
        }
        catch (Exception e)
        {
            log.error("The server could not be reached.\nPlease try again later.");
            e.printStackTrace();
        }
    }

    private void handleRequests()
    {
        log.info("Peer successfully started\nListening for requests...");

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
    				requestThread.shutdown();
    			}
    			if(in.equals("!peers")){
    				System.out.println("Current known peers:");
    				Set<String> peerList = getPeerSnapshot();
    				for(String key : peerList){
    					PeerEndpoint pe = getPeerEndpoint(key);
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
    			if(in.startsWith("!newrequest")){
    				String[] split = in.split(" ");
    				if(split.length!=4){
    					System.out.println("Correct usage is !newrequest <peeraddress> <peerlisteningport> <filelocation>");
    				}else{
    					ApplicationContext ctx = new ClassPathXmlApplicationContext(Constants.SPRINGBEANS);
    					IFingerprintService fingerprintService = (IFingerprintService) ctx.getBean("fingerprintService");
    					Fingerprint finger = fingerprintService.generateFingerprint(split[3].trim());
    					System.out.println("Got Fingerprint");
    					InetAddress adress = InetAddress.getByName(split[1].trim());
    					int port = Integer.parseInt(split[2].trim());
    					System.out.println("Connecting on "+adress+":"+port);
    		            Socket socket = new Socket(adress, port);
    		            ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
    		            P2PSearchRequest request = new P2PSearchRequest();
    		            request.setId("test");
    		            request.setFingerprint(finger);
    		            request.setRequesterAddress(adress);
    		            request.setRequesterPort(port);
    		            request.setTtl(10);
    		            socketout.writeObject(request);
    		            socketout.close();
    				}
    			}
    		} catch (IOException e) {
    			log.error("Error while Commandhandling");
    			e.printStackTrace();
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

    public void setListeningPort(int listeningPort) {
		this.listeningPort = listeningPort;
	}

	public void setKeepAlivePort(int keepAlivePort) {
		this.keepAlivePort = keepAlivePort;
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
    
    public void setKeepAliveCleanup(KeepAliveCleanupThread keepAliveCleanup) {
		this.keepAliveCleanup = keepAliveCleanup;
	}

	public void setRequestThread(RequestThread requestThread) {
		this.requestThread = requestThread;
	}

	public void addFingerprint(Fingerprint fingerprint){
    	System.out.println("Adding Fingerprint: "+fingerprint.getShiftDuration());
    	synchronized(fingerprintList){
    		fingerprintList.add(fingerprint);
    	}
    }
    
    public void addPeerEndpoint(PeerEndpoint peer){
    	synchronized(peerMap){
    		peerMap.put(peer.getAddress()+":"+peer.getListeningPort(), peer);
    	}
    }
    
    public Set<String> getPeerSnapshot(){
    	synchronized(peerMap){
    		return new HashSet<String>(peerMap.keySet());
    	}
    }
    
    public PeerEndpoint getPeerEndpoint(String key){
    	synchronized(peerMap){
    		return peerMap.get(key);
    	}
    }
    
    public List<Fingerprint> getFingerprintSnapshot(){
    	synchronized(fingerprintList){
    		List<Fingerprint> ret = new ArrayList<Fingerprint>(fingerprintList);
    		return ret;
    	}
    }
    
    public void removePeerEndpoint(String key) {
		synchronized(peerMap){
			if(peerMap.containsKey(key)){
				peerMap.remove(key);
			}
		}
	}

	public int getPeerCount() {
		synchronized(peerMap){
			return peerMap.size();
		}
	}

	public void requestNewPeersFromServer() {
		List<PeerEndpoint> peers = serverHandler.getNeighbors(username, password);
        for(PeerEndpoint pe : peers){
        	addPeerEndpoint(pe);
        }
	}

	public boolean sendKeepAliveToServer() {
		return serverHandler.isAlive(username, password, listeningPort, keepAlivePort);	
	}
    
}

	
