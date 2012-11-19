package sa12.group9.common.media;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;

public class FingerprintService implements IFingerprintService
{
    private float herz;
    
    public Fingerprint generateFingerprint(String path) throws IOException
    {
        FingerprintSystem system = new FingerprintSystem(herz);
        return system.fingerprint(Files.readAllBytes(Paths.get(path)));
    }

    public void setHerz(float herz)
    {
        this.herz = herz;
    }
}
