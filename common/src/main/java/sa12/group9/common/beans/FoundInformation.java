package sa12.group9.common.beans;

import java.io.Serializable;

public class FoundInformation extends SongMetadata implements Serializable
{
    private double match;
    private String peerUsername;

    public FoundInformation(SongMetadata meta)
    {
        album = meta.album;
        genre = meta.genre;
        interpret = meta.interpret;
        length = meta.length;
        title = meta.title;
    }

    public FoundInformation()
    {}

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
