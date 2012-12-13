package sa12.group9.peer.cli;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sa12.group9.common.media.IFingerprintService;
import sa12.group9.common.util.Constants;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class Console
{
    private int managementPort;
    private String location;

    private IFingerprintService fingerprintService;

    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.out.println("Wrong usage: <managementPort> <locationOfFile>");
        }
        else
        {
            try
            {
                int managementPort = Integer.parseInt(args[0]);
                new Console(managementPort, args[1]);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please enter a number as the managementPort!");
            }
        }
    }

    public Console(int managementPort, String location)
    {
        this.managementPort = managementPort;
        this.location = location;
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext(Constants.SPRINGBEANS);
        fingerprintService = (IFingerprintService) ctx.getBean("fingerprintService");
        
        
        calculate();
    }

    private void calculate()
    {
        System.out.println("Calculating fingerprint...");
        try
        {
            Fingerprint finger = fingerprintService.generateFingerprint(location);
            Socket socket = new Socket(InetAddress.getLocalHost(), managementPort);
            PrintWriter socketout = new PrintWriter(socket.getOutputStream(), true);
            socketout.println(location);
        }
        catch (IOException e)
        {
            System.out.println("The audio file could not be found!");
        }
    }
}
