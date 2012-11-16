package sa12.group9.client.gui.misc;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class AudioFilter extends FileFilter
{
    public final static String ogg = "ogg";
    public final static String wav = "wav";
    public final static String mp3 = "mp3";
    public final static String flac = "flac";
    public final static String aac = "aac";
    public final static String wma = "wma";

    @Override
    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return true;
        }

        String extension = getExtension(f);
        if (extension != null)
        {
            return extension.equals(ogg) || extension.equals(wav) || extension.equals(mp3) || extension.equals(flac) || extension.equals(wma) || extension.equals(aac);
        }

        return false;
    }

    @Override
    public String getDescription()
    {
        return "Audio files";
    }

    private String getExtension(File f)
    {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1)
        {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
