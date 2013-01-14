package sa12.group9.peer.service.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import sa12.group9.peer.service.Kernel;

public class KeepAliveCleanupThread extends Thread
{
    private Kernel kernel;

    @Override
    public void run()
    {
    	try {
    		kernel.cleanupPeerEndpoints();
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.out.println("Exception in KeepAliveCleanupThread\n"+e.getMessage());
		}
    }
    
    public void setKernel(Kernel kernel){
    	this.kernel = kernel;
    }
}
