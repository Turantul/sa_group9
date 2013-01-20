package sa12.group9.common.beans;

import java.util.ArrayList;
import java.util.List;

public class PeerList
{
    private List<PeerEndpoint> peers;

    public PeerList()
    {
        peers = new ArrayList<PeerEndpoint>();
    }

    public List<PeerEndpoint> getPeers()
    {
        return peers;
    }

    public void setPeers(List<PeerEndpoint> peers)
    {
        this.peers = peers;
    }
}
