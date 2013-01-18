package sa12.group9.peer.service.thread;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.peer.service.Kernel;

public class KeepAliveIncomingHandlerThread extends Thread
{
	private static Log log = LogFactory.getLog(KeepAliveIncomingHandlerThread.class);
	
    private Kernel kernel;
    private DatagramPacket packet;    
    
    public KeepAliveIncomingHandlerThread(DatagramPacket packet, Kernel kernel){
    	this.kernel = kernel;
    	this.packet = packet;
    }
    
    @Override
    public void run()
    {
    	String message = new String(packet.getData());
    	String[] splitmessage = message.split(" ");
		if(splitmessage[0].equals("!alive")){
			try{
				PeerEndpoint pe = new PeerEndpoint();
				pe.setListeningPort(Integer.parseInt(splitmessage[1].trim()));
				pe.setKeepAlivePort(Integer.parseInt(splitmessage[2].trim()));
				pe.setAddress(packet.getAddress().getHostAddress());
				pe.setLastKeepAlive(new Date(System.currentTimeMillis()));
				kernel.addPeerEndpoint(pe);
			}catch(Exception e){
				log.error("Received invalid isAlive package: "+message+"\n");
			}
		}
    }
}
