package sa12.group9.common.beans;

import java.io.Serializable;

public class SongMetadata implements Serializable
{
	protected String interpret;
    protected String title;
    protected int length;
    protected String album;
    protected String genre;

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
    
    public String toString()
    {
    	String s = "";
    	s += "Interpret: " + interpret;
    	s += "\nTitle: " + title;
    	s += "\nLength: " + length;
    	s += "\nAlbum: " + album;
    	s += "\nGenre: " + genre;
    	
    	return s;
    }
}
