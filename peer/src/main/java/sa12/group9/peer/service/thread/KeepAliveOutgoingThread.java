package sa12.group9.peer.service.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.peer.service.IPeerManager;
import sa12.group9.peer.service.IServerHandler;

public class KeepAliveOutgoingThread extends AliveThread
{
	private static Log log = LogFactory.getLog(KeepAliveOutgoingThread.class);
	
    private int listeningPort;
    private int keepAlivePort;
    private int keepAliveOutgoingInterval;
    private Boolean sending = true;
    
    private IPeerManager peerManager;

    @Override
    public void run()
    {
    	do{
	        Set<String> peerSnapshot = peerManager.getPeerSnapshot();
	        for(String key : peerSnapshot){
	        	PeerEndpoint pe = peerManager.getPeerEndpoint(key);
	        	try {
					DatagramSocket datagram = new DatagramSocket();
					String message = "!alive "+listeningPort+" "+keepAlivePort;
					byte[] buf = message.getBytes();
					DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(pe.getAddress()), pe.getKeepAlivePort());
					datagram.send(packet);
				} catch (SocketException e) {
					log.error("Error sending keepAlive to "+pe.getAddress()+":"+pe.getKeepAlivePort()+"\n"+e.getMessage());
				} catch (UnknownHostException e) {
					log.error("Cannot sent keepAlive to adress "+pe.getAddress()+"\n"+e.getMessage());
				} catch (IOException e) {
					log.error("Failed to send keepAlive message\n"+e.getMessage());
				}
	        }
	        kernel.sendKeepAliveToServer();
	        try {
				Thread.sleep(keepAliveOutgoingInterval);
			} catch (InterruptedException e) {
				log.error("Keep Alive Thread has been interrupted.\n"+e.getMessage());
			}
        }while(sending);
    }

    public void setListeningPort(int listeningPort)
    {
        this.listeningPort = listeningPort;
    }

    public void setKeepAlivePort(int keepAlivePort)
    {
        this.keepAlivePort = keepAlivePort;
    }

	public void setKeepAliveOutgoingInterval(int keepAliveOutgoingInterval) {
		this.keepAliveOutgoingInterval = keepAliveOutgoingInterval;
	}

	public void setPeerManager(IPeerManager peerManager) {
		this.peerManager = peerManager;
	}

	public void shutdown(){
		log.debug("Shutdown KeepAliveOutgoingThread.");
    	sending = false;
    }
    
}
