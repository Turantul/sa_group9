package sa12.group9.peermanagement.cli;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.ManagementCommandResponse;

public class CommandResponseListener extends Thread
{
    private static Log log = LogFactory.getLog(CommandResponseListener.class);

    private Socket socket;

    public CommandResponseListener(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try
        {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ManagementCommandResponse response = (ManagementCommandResponse) in.readObject();
            System.out.println(response.getCommandResponse());
            socket.close();
        }
        catch (IOException e)
        {
            if (!socket.isClosed())
            {
                log.error("Error while listening for ManagementCommandResponse.");
            }
        }
        catch (ClassNotFoundException e)
        {
            log.error("Error reading ManagementCommandResponse object.");
        }
    }

    public void shutdown()
    {
        log.debug("Shutdown CommandResponseListener");
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            log.error("Error closing management command listener connection.\n" + e.getMessage());
        }

    }
}
