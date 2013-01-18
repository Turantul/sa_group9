package sa12.group9.common.beans;

import java.util.ArrayList;
import java.util.List;

import sa12.group9.commons.dto.PeerDTO;

public class PeerList
{
    private List<PeerDTO> peers;
    
    public PeerList()
    {
        peers = new ArrayList<PeerDTO>();
    }

    public List<PeerDTO> getPeers()
    {
        return peers;
    }

    public void setPeers(List<PeerDTO> peers)
    {
        this.peers = peers;
    }
}
