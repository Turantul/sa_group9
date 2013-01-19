package sa12.group9.common.beans;

import java.io.Serializable;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class P2PSearchRequest implements Serializable
{
    private String id;
    private String requesterAddress;
    private int requesterPort;
    private Fingerprint fingerprint;
    private int ttl;
    private int maxPeersForForwarding;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getRequesterAddress()
    {
        return requesterAddress;
    }

    public void setRequesterAddress(String requesterAddress)
    {
        this.requesterAddress = requesterAddress;
    }

    public int getRequesterPort()
    {
        return requesterPort;
    }

    public void setRequesterPort(int requesterPort)
    {
        this.requesterPort = requesterPort;
    }

    public Fingerprint getFingerprint()
    {
        return fingerprint;
    }

    public void setFingerprint(Fingerprint fingerprint)
    {
        this.fingerprint = fingerprint;
    }

    public int getTtl()
    {
        return ttl;
    }

    public void setTtl(int ttl)
    {
        this.ttl = ttl;
    }

    public int getMaxPeersForForwarding()
    {
        return maxPeersForForwarding;
    }

    public void setMaxPeersForForwarding(int maxPeersForForwarding)
    {
        this.maxPeersForForwarding = maxPeersForForwarding;
    }

}
