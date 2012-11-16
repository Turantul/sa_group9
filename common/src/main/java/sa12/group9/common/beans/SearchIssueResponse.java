package sa12.group9.common.beans;

import java.util.List;

public class SearchIssueResponse
{
    private String errorMsg;
    private List<PeerEndpoint> peers;
    private int ttl;

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public List<PeerEndpoint> getPeers()
    {
        return peers;
    }

    public void setPeers(List<PeerEndpoint> peers)
    {
        this.peers = peers;
    }

    public int getTtl()
    {
        return ttl;
    }

    public void setTtl(int ttl)
    {
        this.ttl = ttl;
    }
}
