package sa12.group9.common.media;

import java.io.IOException;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

/**
 * Service interface to deal with fingerprints 
 */
public interface IFingerprintService
{
    /**
     * Calculates the fingerprint for a specified file
     * 
     * @param path to the file to be fingerprinted
     * @return Fingerprint
     * @throws IOException if the file could not be found or there was an error reading it
     */
    Fingerprint generateFingerprint(String path) throws IOException;
}
