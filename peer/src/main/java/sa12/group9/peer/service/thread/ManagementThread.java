package sa12.group9.peer.service.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ManagementThread extends Thread
{
    private int managementPort;
    private ServerSocket socket;

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
				Socket inSocket = socket.accept();
				new ManagementCommandHandler(inSocket);
			} catch (IOException e) {
				System.out.println("Exception while reading from ManagementSocket.\n"+e.getMessage());
			}
		}
    }

    public void setManagementPort(int managementPort)
    {
        this.managementPort = managementPort;
    }
}
