package sa12.group9.common.media;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import sa12.group9.common.beans.SongMetadata;

public class SongMetadataService implements ISongMetadataService
{
    public SongMetadata getSongMetadata(String path) throws IOException
    {
        File f = new File(path);
        AudioFile af = null;
        SongMetadata smd = new SongMetadata();
        try
        {
            af = AudioFileIO.read(f);
            Tag tag = af.getTag();
            smd.setLength(af.getAudioHeader().getTrackLength());
            smd.setInterpret(tag.getFirst(FieldKey.ARTIST));
            smd.setAlbum(tag.getFirst(FieldKey.ALBUM));
            smd.setTitle(tag.getFirst(FieldKey.TITLE));
            smd.setGenre(tag.getFirst(FieldKey.GENRE));
        }
        catch (Exception e)
        {
            System.out.println("Error determining song metadata.");
        }
        return smd;
    }

}
