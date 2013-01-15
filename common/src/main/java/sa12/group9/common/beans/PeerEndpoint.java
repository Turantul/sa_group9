package sa12.group9.common.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="peers")
public class PeerEndpoint
{
	@Id
    private String address;
    private int listeningPort;
    private int keepAlivePort;

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
}