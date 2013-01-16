package sa12.group9.peer.service.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sa12.group9.peer.service.Kernel;

public class RequestThread extends Thread {
	
	
	private ServerSocket serverSocket;
	private ExecutorService pool;
	private Kernel kernel;
	private int listeningPort;
	
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
                    pool.execute(new RequestHandler(socket, kernel));
                }
                catch (IOException e)
                {
                    if (!serverSocket.isClosed())
                    {
                        System.out.println("Error reading from TCP socket!");
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error opening TCP socket!");
        }
    }

	public void setKernel(Kernel kernel) {
		this.kernel = kernel;
	}

	public void setListeningPort(int listeningPort) {
		this.listeningPort = listeningPort;
	}

	public void shutdown() {
		pool.shutdown();
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Error closing Socket.\n"+e.getMessage());
		}		
	}
    
    
}
