package sa12.group9.common.beans;

import java.util.Date;

public class PeerEndpoint
{
    private String address;
    private int listeningPort;
    private int keepAlivePort;
    private Date lastKeepAlive;

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public int getListeningPort()
    {
        return listeningPort;
    }

    public void setListeningPort(int listeningPort)
    {
        this.listeningPort = listeningPort;
    }

    public int getKeepAlivePort()
    {
        return keepAlivePort;
    }

    public void setKeepAlivePort(int keepAlivePort)
    {
        this.keepAlivePort = keepAlivePort;
    }

	public Date getLastKeepAlive() {
		return lastKeepAlive;
	}

	public void setLastKeepAlive(Date lastKeepAlive) {
		this.lastKeepAlive = lastKeepAlive;
	}
    
}