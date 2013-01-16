package sa12.group9.peer.service.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import sa12.group9.peer.service.Kernel;

public class ManagementThread extends Thread
{
    private int managementPort;
    private ServerSocket socket;
    private Kernel kernel;

    @Override
    public void run()
    {
    	BufferedReader socketin = null;
    	try{
    		socket = new ServerSocket(managementPort);
    	}catch(Exception e){
    		System.out.println("Exception when creating ManagementListener.\n"+e.getMessage());
    	}
    	
    	while(!socket.isClosed()){
			try {
				System.out.println("Waiting for ManagementCommands on Port: "+managementPort);
				Socket inSocket = socket.accept();
				System.out.println("Recieved Connection");
				new ManagementCommandHandler(inSocket, kernel).start();
			} catch (IOException e) {
				System.out.println("Exception while reading from ManagementSocket.\n"+e.getMessage());
			}
		}
    }

    public void setManagementPort(int managementPort)
    {
        this.managementPort = managementPort;
    }
    
    public void setKernel(Kernel kernel){
    	this.kernel = kernel;
    }
    
    public void shutdown(){
    	try {
			socket.close();
		} catch (IOException e) {
			System.out.println("Error closing management connection.\n"+e.getMessage());
		}
    	
    }
}
