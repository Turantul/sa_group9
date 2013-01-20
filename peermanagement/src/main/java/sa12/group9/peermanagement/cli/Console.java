package sa12.group9.peermanagement.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.ManagementCommand;
import sa12.group9.common.beans.SongMetadata;
import sa12.group9.common.media.IFingerprintService;
import sa12.group9.common.media.ISongMetadataService;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class Console
{
    private static Log log = LogFactory.getLog(Console.class);

    private IFingerprintService fingerprintService;
    private ISongMetadataService songMetadataService;

    @SuppressWarnings("unused")
    private void initialize()
    {
        System.out.println("Welcome to the Swazam management console.");
        System.out.println("Type !help to get information about the available commands");

        InputStreamReader cin = new InputStreamReader(System.in);
        BufferedReader bin = new BufferedReader(cin);

        while (true)
        {
            try
            {
                String in = bin.readLine();
                if (in.equals("!help"))
                {
                    System.out.println("Available commands are:");
                    System.out.println("!exit - exit the program");
                    System.out.println("!files - get information about the stored files on a peer");
                    System.out.println("!addfile - add file to peer");
                    System.out.println("!removefile - remove file from peer");
                    System.out.println("!stats - get statistics about the peer");
                    System.out.println("Use the command without parameters to get information about the command");
                }
                if (in.equals("!exit"))
                {
                    System.out.println("Exiting the management program");
                    break;
                }
                if (in.startsWith("!files"))
                {
                    String[] split = in.split(" ");
                    if (split.length != 3)
                    {
                        System.out.println("Correct usage is !files <peeraddress> <peermanagementport>");
                    }
                    else
                    {
                        ManagementCommand command = new ManagementCommand(in);
                        sendCommand(split[1].trim(), Integer.parseInt(split[2].trim()), command);
                    }
                }
                if (in.startsWith("!addfile"))
                {
                    String[] split = in.split(" ");
                    if (split.length < 4)
                    {
                        System.out.println("Correct usage is !addfile <peeraddress> <peermanagementport> <filelocation>");
                    }
                    else
                    {
                        ManagementCommand command = new ManagementCommand(in);

                        String fileLocation = "";
                        if (split.length > 3)
                        {
                            for (int i = 3; i < split.length; i++)
                            {
                                fileLocation += split[i];
                            }
                        }
                        File file = new File(fileLocation);
                        if (!file.exists())
                        {
                            System.out.println("File " + fileLocation + " does not exist.");
                        }
                        else
                        {
                        	System.out.println("Parsing file. This might take a while ...");
                            Fingerprint finger = fingerprintService.generateFingerprint(fileLocation);
                            command.setFingerprint(finger);
                            SongMetadata smd = songMetadataService.getSongMetadata(fileLocation);
                            
                            if (smd.getTitle() == null || smd.getTitle().equals(""))
                            {
                                System.out.println("This file has no title. Please enter it: ");
                                smd.setTitle(bin.readLine());
                            }
                            if (smd.getInterpret() == null || smd.getInterpret().equals(""))
                            {
                                System.out.println("This file has no interpret. Please enter it: ");
                                smd.setInterpret(bin.readLine());
                            }
                            
                            command.setSongMetadata(smd);
                            sendCommand(split[1].trim(), Integer.parseInt(split[2].trim()), command);
                        }
                    }
                }
                if (in.startsWith("!removefile"))
                {
                    String[] split = in.split(" ");
                    if (split.length != 4)
                    {
                        System.out.println("Correct usage is !removefile <peeraddress> <peermanagementport> <filenumber>");
                    }
                    else
                    {
                        ManagementCommand command = new ManagementCommand(in);
                        sendCommand(split[1].trim(), Integer.parseInt(split[2].trim()), command);
                    }
                }
                if (in.startsWith("!stats"))
                {
                    String[] split = in.split(" ");
                    if (split.length != 3)
                    {
                        System.out.println("Correct usage is !stats <peeraddress> <peermanagementport>");
                    }
                    else
                    {
                        ManagementCommand command = new ManagementCommand(in);
                        sendCommand(split[1].trim(), Integer.parseInt(split[2].trim()), command);
                    }
                }
            }
            catch (IOException e)
            {
                log.error("Error sending command to peer. Maybe you entered the wrong address or peer is not online?");
            }
        }
    }
    
    private void sendCommand(String host, int port, ManagementCommand command) throws UnknownHostException, IOException
    {
    	try {
        	Socket socket = new Socket(host, port);
        	ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
            socketout.writeObject(command);
            new CommandResponseListener(socket).start();
        } catch (IllegalArgumentException iae) {
        	System.out.println("Enter a port in the range 1-65535");
        }
    }
    
    public void setFingerprintService(IFingerprintService fingerprintService)
    {
        this.fingerprintService = fingerprintService;
    }

    public void setSongMetadataService(ISongMetadataService songMetadataService)
    {
        this.songMetadataService = songMetadataService;
    }
}
