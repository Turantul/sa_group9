package sa12.group9.common.beans;

public class SearchIssueResponse
{
    private String errorMsg;
    private PeerList peers;
    private int ttl;
    private int secondsToWait;
    private int maxPeersForForwarding;

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public PeerList getPeers()
    {
        return peers;
    }

    public void setPeers(PeerList peers)
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

    public int getMaxPeersForForwarding()
    {
        return maxPeersForForwarding;
    }

    public void setMaxPeersForForwarding(int maxPeersForForwarding)
    {
        this.maxPeersForForwarding = maxPeersForForwarding;
    }
}
