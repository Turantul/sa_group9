package sa12.group9.peer.service.thread;

public class ManagementThread extends Thread
{
    private int managementPort;

    @Override
    public void run()
    {
        // TODO listen for command line actions
    }

    public void setManagementPort(int managementPort)
    {
        this.managementPort = managementPort;
    }
}
