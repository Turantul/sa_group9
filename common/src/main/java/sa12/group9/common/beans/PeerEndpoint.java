package sa12.group9.common.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection="peers")
public class PeerEndpoint
{
	@Id
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