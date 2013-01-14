package sa12.group9.peer.service.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class KeepAliveIncomingThread extends AliveThread
{
    private int keepAlivePort;
    private DatagramSocket datagram;
    
    
    public KeepAliveIncomingThread(){
    	
    }
    
    @Override
    public void run()
    {
    	try{
			this.datagram = new DatagramSocket(keepAlivePort, InetAddress.getByName("localhost"));
			
		}catch(Exception e){
			System.out.println("Error creating KeepAliveSocket\n"+e.getMessage());
		}
    	byte[] buf= new byte[50];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		System.out.println("Waiting for other peers on "+datagram.getLocalAddress()+" "+datagram.getLocalPort());
		while(!datagram.isClosed()){
			try {
				datagram.receive(packet);
				new KeepAliveIncomingHandlerThread(packet, kernel).start();
			} catch (IOException e) {
				System.out.println("Error receiving KeepAlive message\n"+e.getMessage());
			}
		}
    }

    public void setKeepAlivePort(int keepAlivePort)
    {
        this.keepAlivePort = keepAlivePort;
    }
    
    public void shutdown(){
		datagram.close();
	}
}
