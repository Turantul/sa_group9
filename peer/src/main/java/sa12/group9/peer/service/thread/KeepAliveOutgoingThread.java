package sa12.group9.peer.service.thread;

import sa12.group9.peer.service.IServerHandler;

public class KeepAliveOutgoingThread extends AliveThread
{
    private IServerHandler serverHandler;

    private int listeningPort;
    private int keepAlivePort;

    @Override
    public void run()
    {
        // TODO: send keepAlives to neighbors and server
    }

    public void setServerHandler(IServerHandler serverHandler)
    {
        this.serverHandler = serverHandler;
    }

    public void setListeningPort(int listeningPort)
    {
        this.listeningPort = listeningPort;
    }

    public void setKeepAlivePort(int keepAlivePort)
    {
        this.keepAlivePort = keepAlivePort;
    }
}
