package sa12.group9.peer.service.thread;

import java.net.DatagramPacket;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.peer.service.IPeerManager;
import sa12.group9.peer.service.Kernel;

public class KeepAliveIncomingHandlerThread extends Thread
{
    private static Log log = LogFactory.getLog(KeepAliveIncomingHandlerThread.class);

    private Kernel kernel;
    private DatagramPacket packet;

    private IPeerManager peerManager;

    public KeepAliveIncomingHandlerThread(DatagramPacket packet, Kernel kernel, IPeerManager peerManager)
    {
        this.kernel = kernel;
        this.packet = packet;
        this.peerManager = peerManager;
    }

    @Override
    public void run()
    {
        String message = new String(packet.getData());
        String[] splitmessage = message.split(" ");
        if (splitmessage[0].equals("!alive"))
        {
            try
            {
                PeerEndpoint pe = new PeerEndpoint();
                pe.setListeningPort(Integer.parseInt(splitmessage[1].trim()));
                pe.setKeepAlivePort(Integer.parseInt(splitmessage[2].trim()));
                pe.setAddress(packet.getAddress().getHostAddress());
                pe.setLastKeepAlive(new Date(System.currentTimeMillis()));
                if (!(pe.getAddress().equals("127.0.0.1") && pe.getListeningPort() == kernel.getListeningPort() && pe.getKeepAlivePort() == kernel.getKeepAlivePort()))
                {
                    peerManager.addPeerEndpoint(pe);
                }
            }
            catch (Exception e)
            {
                log.error("Received invalid isAlive package: " + message + "\n");
            }
        }
    }
}
