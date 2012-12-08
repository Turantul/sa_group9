package sa12.group9.common.beans;

import java.util.List;

public class SearchIssueResponse
{
    private String errorMsg;
    private List<PeerEndpoint> peers;
    private int ttl;
    private int secondsToWait;

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

    public int getSecondsToWait()
    {
        return secondsToWait;
    }

    public void setSecondsToWait(int secondsToWait)
    {
        this.secondsToWait = secondsToWait;
    }
}
