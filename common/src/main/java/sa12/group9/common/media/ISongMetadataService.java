package sa12.group9.common.media;

import java.io.IOException;

import sa12.group9.common.beans.SongMetadata;

public interface ISongMetadataService
{
    /**
     * Retrieves the SongMetadata from a specified file
     * 
     * @param path to the file
     * @return SongMetadata
     * @throws IOException if the file could not be found or there was an error
     *         reading it
     */
    SongMetadata getSongMetadata(String path) throws IOException;
}
