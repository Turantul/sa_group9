package sa12.group9.peer.service.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.peer.service.IServerHandler;

public class KeepAliveOutgoingThread extends AliveThread
{
    private IServerHandler serverHandler;

    private int listeningPort;
    private int keepAlivePort;
    private int keepAlivePeriod = 2500;
    private Boolean sending = true;

    @Override
    public void run()
    {
    	do{
	        List<PeerEndpoint> peerSnapshot = kernel.getPeerSnapshot();
	        for(PeerEndpoint pe : peerSnapshot){
	        	try {
					DatagramSocket datagram = new DatagramSocket();
					String message = "!alive "+listeningPort+" "+keepAlivePort;
					byte[] buf = message.getBytes();
					DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(pe.getAddress()), pe.getKeepAlivePort());
					datagram.send(packet);
					System.out.println("Sent KeepAlive to "+packet.getAddress()+":"+packet.getPort()+"-"+packet.getSocketAddress());
				} catch (SocketException e) {
					System.out.println("Error sending keepAlive to "+pe.getAddress()+":"+pe.getKeepAlivePort()+"\n"+e.getMessage());
				} catch (UnknownHostException e) {
					System.out.println("Cannot sent keepAlive to adress "+pe.getAddress()+"\n"+e.getMessage());
				} catch (IOException e) {
					System.out.println("Failed to send keepAlive message\n"+e.getMessage());
				}
	        }
	        try {
				Thread.sleep(keepAlivePeriod);
			} catch (InterruptedException e) {
				System.out.println("Keep Alive Thread has been interrupted.\n"+e.getMessage());
			}
        }while(sending);
    }

    public void setServerHandler(IServerHandler serverHandler)
    {
        this.serverHandler = serverHandler;
    }

    public void setListeningPort(int listeningPort)
    {
        this.listeningPort = listeningPort;
    }

    public void setKeepAlivePort(int keepAlivePort)
    {
        this.keepAlivePort = keepAlivePort;
    }
    
    public void setKeepAlivePeriod(int keepAlivePeriod) {
		this.keepAlivePeriod = keepAlivePeriod;
	}

	public void shutdown(){
    	sending = false;
    }
    
}
