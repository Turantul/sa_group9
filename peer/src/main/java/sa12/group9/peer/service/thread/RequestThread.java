package sa12.group9.peer.service.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.peer.service.Kernel;

public class RequestThread extends Thread {
	
	private static Log log = LogFactory.getLog(RequestThread.class);
	
	private ServerSocket serverSocket;
	private ExecutorService pool;
	private Kernel kernel;
	private int listeningPort;
	private int forwardPeerNumber;
	
	public RequestThread(){
		pool = Executors.newCachedThreadPool();
	}
	
	@Override
    public void run()
    {
        try
        {
            serverSocket = new ServerSocket(listeningPort);
            while (!serverSocket.isClosed())
            {
                try
                {
                    Socket socket = serverSocket.accept();
                    System.out.println("Accepting connection");
                    pool.execute(new RequestHandler(socket, kernel, forwardPeerNumber));
                }
                catch (IOException e)
                {
                    if (!serverSocket.isClosed())
                    {
                        log.error("Error reading from TCP socket!");
                    }
                }
            }
        }
        catch (IOException e)
        {
            log.error("Error opening TCP socket on port "+listeningPort);
        }
    }

	public void setKernel(Kernel kernel) {
		this.kernel = kernel;
	}

	public void setListeningPort(int listeningPort) {
		this.listeningPort = listeningPort;
	}

	public void setForwardPeerNumber(int forwardPeerNumber) {
		this.forwardPeerNumber = forwardPeerNumber;
	}

	public void shutdown() {
		log.debug("Shutdown RequestThread");
		pool.shutdown();
		try {
			serverSocket.close();
		} catch (IOException e) {
			log.error("Error closing Socket.\n"+e.getMessage());
		}		
	}
    
    
}
