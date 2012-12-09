package sa12.group9.peer.service.thread;

public class KeepAliveIncomingThread extends AliveThread
{
    private int keepAlivePort;
    
    @Override
    public void run()
    {
        // TODO listen for keepAlives and maintain neighbor list (synchronized)
    }

    public void setKeepAlivePort(int keepAlivePort)
    {
        this.keepAlivePort = keepAlivePort;
    }
}
