package sa12.group9.common.beans;

public class IsAliveNotification extends LoginRequest
{
    private int listeningPort;
    private int keepAlivePort;

    public int getListeningPort()
    {
        return listeningPort;
    }

    public void setListeningPort(int listeningPort)
    {
        this.listeningPort = listeningPort;
    }

    public int getKeepAlivePort()
    {
        return keepAlivePort;
    }

    public void setKeepAlivePort(int keepAlivePort)
    {
        this.keepAlivePort = keepAlivePort;
    }
}
