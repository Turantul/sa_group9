package sa12.group9.common.beans;

public class FoundNotification
{
    private String interpret;
    private String title;
    private int length;
    private String album;
    private String genre;
    
    private String peerUsername;
    
    public String getInterpret()
    {
        return interpret;
    }
    
    public void setInterpret(String interpret)
    {
        this.interpret = interpret;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public String getPeerUsername()
    {
        return peerUsername;
    }

    public void setPeerUsername(String peerUsername)
    {
        this.peerUsername = peerUsername;
    }
}
