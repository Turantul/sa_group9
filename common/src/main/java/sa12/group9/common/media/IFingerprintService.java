package sa12.group9.common.media;

import java.io.IOException;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public interface IFingerprintService
{
    Fingerprint generateFingerprint(String path) throws IOException;
}
