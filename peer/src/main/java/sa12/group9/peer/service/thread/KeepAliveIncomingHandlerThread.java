package sa12.group9.peer.service.thread;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.peer.service.Kernel;

public class KeepAliveIncomingHandlerThread extends Thread
{
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
				//System.out.println("Recieved KeepAlive from: "+pe.getAddress()+":"+pe.getListeningPort());
				kernel.addPeerEndpoint(pe);
			}catch(Exception e){
				System.out.println("Received invalid isAlive package: "+message+"\n");
				e.printStackTrace();
			}
		}
    }
}
