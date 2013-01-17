package sa12.group9.peer.service.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.peer.service.Kernel;

public class KeepAliveCleanupThread extends Thread
{
    private Kernel kernel;
    private boolean running=true;
    
    public KeepAliveCleanupThread(){
    }
    
    @Override
    public void run()
    {
    	while(running){
	    	try {
	    		Set<String> peerList = kernel.getPeerSnapshot();
	    		for(String key : peerList){
	    			PeerEndpoint pe = kernel.getPeerEndpoint(key);
	    			if(pe.getLastKeepAlive() != null && pe.getLastKeepAlive().getTime()<System.currentTimeMillis()-10000){
	    				kernel.removePeerEndpoint(pe.getAddress()+":"+pe.getListeningPort());
	    				System.out.println("Removed peer "+pe.getAddress()+":"+pe.getListeningPort());
	    			}
	    		}
	    		Thread.sleep(5000);				
			} catch (InterruptedException e) {
				System.out.println("Exception in KeepAliveCleanupThread\n"+e.getMessage());
			}
    	}
    }
    
    public void setKernel(Kernel kernel){
    	this.kernel = kernel;
    }
    
    public void shutdown(){
    	running = false;
    }
}
