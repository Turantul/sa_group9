package sa12.group9.peer.service.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.peer.service.IPeerManager;

public class KeepAliveIncomingThread extends AliveThread
{
	private static Log log = LogFactory.getLog(KeepAliveIncomingThread.class);
	
    private int keepAlivePort;
    private DatagramSocket datagram;
    private IPeerManager peerManager;
    
    public KeepAliveIncomingThread(){
    	
    }
    
    @Override
    public void run()
    {
    	try{
			this.datagram = new DatagramSocket(keepAlivePort, InetAddress.getByName("localhost"));
		}catch(Exception e){
			log.error("Error creating KeepAliveSocket\n"+e.getMessage());
		}
    	byte[] buf= new byte[50];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		System.out.println("Waiting for other peers on "+datagram.getLocalAddress()+" "+datagram.getLocalPort());
		while(!datagram.isClosed()){
			try {
				datagram.receive(packet);
				new KeepAliveIncomingHandlerThread(packet, kernel, peerManager).start();
			} catch (IOException e) {
				if(!datagram.isClosed()){
					log.error("Error receiving KeepAlive message\n"+e.getMessage());
				}
			}
		}
    }

    public void setKeepAlivePort(int keepAlivePort)
    {
        this.keepAlivePort = keepAlivePort;
    }
    
    public void setPeerManager(IPeerManager peerManager) {
		this.peerManager = peerManager;
	}

	public void shutdown(){
    	log.debug("Shutdown KeepAliveIncomingThread.");
		datagram.close();
	}
}
