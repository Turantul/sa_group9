package sa12.group9.peer.service.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.peer.service.IPeerManager;
import sa12.group9.peer.service.Kernel;

public class ManagementThread extends Thread
{
    private static Log log = LogFactory.getLog(ManagementThread.class);

    private int managementPort;
    private ServerSocket socket;
    private Kernel kernel;
    private IPeerManager peerManager;

    @Override
    public void run()
    {
        try
        {
            socket = new ServerSocket(managementPort);
        }
        catch (Exception e)
        {
            log.error("Exception when creating ManagementListener.\n" + e.getMessage());
        }

        while (!socket.isClosed())
        {
            try
            {
                Socket inSocket = socket.accept();
                new ManagementCommandHandler(inSocket, kernel, peerManager).start();
            }
            catch (IOException e)
            {
                if (!socket.isClosed())
                {
                    log.error("Exception while reading from ManagementSocket.\n" + e.getMessage());
                }
            }
        }
    }

    public void setManagementPort(int managementPort)
    {
        this.managementPort = managementPort;
    }

    public void setKernel(Kernel kernel)
    {
        this.kernel = kernel;
    }

    public void setPeerManager(IPeerManager peerManager)
    {
        this.peerManager = peerManager;
    }

    public void shutdown()
    {
        log.debug("Shutdown ManagementThread");
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            log.error("Error closing management connection.\n" + e.getMessage());
        }
    }
}
