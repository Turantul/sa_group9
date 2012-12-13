package sa12.group9.common.beans;

import java.io.Serializable;
import java.net.InetAddress;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class P2PSearchRequest implements Serializable{

	private String id;
	private InetAddress requesterAddress;
	private int requesterPort;
	private Fingerprint fingerprint;
	private int ttl;
	
	public P2PSearchRequest() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InetAddress getRequesterAddress() {
		return requesterAddress;
	}

	public void setRequesterAddress(InetAddress requesterAddress) {
		this.requesterAddress = requesterAddress;
	}

	public int getRequesterPort() {
		return requesterPort;
	}

	public void setRequesterPort(int requesterPort) {
		this.requesterPort = requesterPort;
	}

	public Fingerprint getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(Fingerprint fingerprint) {
		this.fingerprint = fingerprint;
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	
	
	
}
