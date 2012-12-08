package sa12.group9.peer.service;

import sa12.group9.common.media.IFingerprintService;

public class Kernel
{
    private IServerHandler serverHandler;
    private IPeerHandler peerHandler;
    private IFingerprintService fingerprintService;
    
    private String username;
    private String password;
    
    @SuppressWarnings("unused")
    private void initialize()
    {
        
    }

    public void setServerHandler(IServerHandler serverHandler)
    {
        this.serverHandler = serverHandler;
    }

    public void setPeerHandler(IPeerHandler peerHandler)
    {
        this.peerHandler = peerHandler;
    }

    public void setFingerprintService(IFingerprintService fingerprintService)
    {
        this.fingerprintService = fingerprintService;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
