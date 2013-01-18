package sa12.group9.peer.cli;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sa12.group9.common.beans.ManagementCommand;
import sa12.group9.common.beans.P2PSearchRequest;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.media.IFingerprintService;
import sa12.group9.common.util.Constants;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class Console
{
	private static Log log = LogFactory.getLog(Console.class);
	
    private int managementPort;
    private IFingerprintService fingerprintService;
    private Socket listenSocket;
    private CommandResponseListener commandResponseListener;

    public static void main(String[] args)
    {
    	Console console = new Console(12345);
    }

    public Console(int managementPort)
    {
        this.managementPort = managementPort;
        try {
			this.listenSocket = new Socket(InetAddress.getByName("localhost"), managementPort);
			this.commandResponseListener = new CommandResponseListener(listenSocket);
		} catch (UnknownHostException e) {
			log.error("Unknown Host");
		} catch (IOException e) {
			log.error("IOException");
		}
        ApplicationContext ctx = new ClassPathXmlApplicationContext(Constants.SPRINGBEANS);
		IFingerprintService fingerprintService = (IFingerprintService) ctx.getBean("fingerprintService");
        handleCommands();
    }
    
    private void handleCommands(){
    	System.out.println("Welcome to the Swazam management console.");
    	System.out.println("Type !help to get information about the available commands");

        boolean running = true;
        InputStreamReader cin = new InputStreamReader(System.in);
		BufferedReader bin = new BufferedReader(cin);
		String in;
		
        while (running){
        	try {
    			in = bin.readLine();
    			if(in.equals("!help")){
    				System.out.println("Available commands are:");
    				System.out.println("!exit - exit the program");
    				System.out.println("!files - get information about the stored files on a peer");
    				System.out.println("!addfile - add file to peer");
    				System.out.println("!removefile - remove file from peer");
    				System.out.println("!stats - get statistics about the peer");
    				System.out.println("Use the command without parameters to get information about the command");
    			}
    			if(in.equals("!exit")){
    				System.out.println("Exiting the management program");
    				
    			}
    			if(in.startsWith("!files")){
    				String[] split = in.split(" ");
    				if(split.length!=3){
    					System.out.println("Correct usage is !files <peeraddress> <peermanagementport>");
    				}else{
    					ManagementCommand command = new ManagementCommand(in);
    					Socket socket = new Socket(split[1].trim(), Integer.parseInt(split[2].trim()));
    		            ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
    		            socketout.writeObject(command);
    		            new CommandResponseListener(socket).start();
    				}
    			}
    			if(in.startsWith("!addfile")){
    				String[] split = in.split(" ");
    				if(split.length<4){
    					System.out.println("Correct usage is !addfile <peeraddress> <peermanagementport> <filelocation>");
    				}else{
    					ManagementCommand command = new ManagementCommand(in);
    					
    					String fileLocation = "";
    					if(split.length>3){
    						for(int i = 3;i<split.length;i++){
    							fileLocation += split[i];
    						}
    					}
    					File file = new File(fileLocation);
    					if(!file.exists()){
    						System.out.println("File "+fileLocation+" does not exist.");
    					}else{
    						ApplicationContext ctx = new ClassPathXmlApplicationContext(Constants.SPRINGBEANS);
        					IFingerprintService fingerprintService = (IFingerprintService) ctx.getBean("fingerprintService");
        					Fingerprint finger = fingerprintService.generateFingerprint(fileLocation);
        					command.setFingerprint(finger);
    						Socket socket = new Socket(split[1].trim(), Integer.parseInt(split[2].trim()));
        		            ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
        		            socketout.writeObject(command);
        		            new CommandResponseListener(socket).start();
    					}
    				}
    			}
    			if(in.startsWith("!removefile")){
    				String[] split = in.split(" ");
    				if(split.length!=3){
    					System.out.println("Correct usage is !removefile <peeraddress> <peermanagementport> <filenumber>");
    				}else{
    					ManagementCommand command = new ManagementCommand(in);
    					Socket socket = new Socket(split[1].trim(), Integer.parseInt(split[2].trim()));
    		            ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
    		            socketout.writeObject(command);
    		            new CommandResponseListener(socket).start();
    				}
    			}
    			if(in.startsWith("!stats")){
    				String[] split = in.split(" ");
    				if(split.length!=3){
    					System.out.println("Correct usage is !stats <peeraddress> <peermanagementport>");
    				}else{
    					ManagementCommand command = new ManagementCommand(in);
    					Socket socket = new Socket(split[1].trim(), Integer.parseInt(split[2].trim()));
    		            ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
    		            socketout.writeObject(command);
    		            new CommandResponseListener(socket).start();
    				}
    			}
    		} catch (IOException e) {
    			log.error("Error while Commandhandling");
    			e.printStackTrace();
    		}
        }
    }

    private void calculate()
    {
       /* System.out.println("Calculating fingerprint...");
        try
        {
            Fingerprint finger = fingerprintService.generateFingerprint(location);
            System.out.println("Sending fingerprint to peer on Port: "+managementPort);
            Socket socket = new Socket(InetAddress.getLocalHost(), managementPort);
            ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
            socketout.writeObject(finger);
            socketout.close();
            System.exit(0);
        }
        catch (IOException e)
        {
            System.out.println("The audio file could not be found!");
        }*/
    }
}
