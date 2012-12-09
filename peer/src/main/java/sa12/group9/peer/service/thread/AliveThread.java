package sa12.group9.peer.service.thread;

import java.util.List;

import sa12.group9.common.beans.PeerEndpoint;

public abstract class AliveThread extends Thread
{
    protected List<PeerEndpoint> peers;
    
    public void setPeers(List<PeerEndpoint> peers)
    {
        this.peers = peers;
    }
}
