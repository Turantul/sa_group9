package sa12.group9.peer.service.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import sa12.group9.common.beans.P2PSearchRequest;
import sa12.group9.peer.service.Kernel;

public class RequestHandler extends Thread
{
    private Socket socket;
    private Kernel kernel;
    
    public RequestHandler(Socket socket, Kernel kernel)
    {
        this.socket = socket;
        this.kernel = kernel;
    }
    
    @Override
    public void run()
    {
    	try{
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			while(!socket.isClosed()){
				P2PSearchRequest input = (P2PSearchRequest) in.readObject();
				System.out.println("Got Request "+input.getId()+" "+input.getFingerprint());
			}
		}catch(IOException e){
			System.out.println("Error reading IO Command in ManagementHandler. \n"+e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Problem reading fingerprint. \n"+e.getMessage());
		}
    }
}
