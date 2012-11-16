package sa12.group9.common.beans;

public class SuccessRequest extends SearchIssueRequest
{
    private String peerUsername;

    public String getPeerUsername()
    {
        return peerUsername;
    }

    public void setPeerUsername(String peerUsername)
    {
        this.peerUsername = peerUsername;
    }
}
