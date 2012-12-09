package sa12.group9.common.beans;

public class FoundInformation extends SongMetadata
{
    private double match;
    private String peerUsername;

    public String getPeerUsername()
    {
        return peerUsername;
    }

    public void setPeerUsername(String peerUsername)
    {
        this.peerUsername = peerUsername;
    }

    public double getMatch()
    {
        return match;
    }

    public void setMatch(double match)
    {
        this.match = match;
    }
}
