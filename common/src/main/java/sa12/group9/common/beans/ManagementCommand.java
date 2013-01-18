package sa12.group9.common.beans;

import java.io.Serializable;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class ManagementCommand implements Serializable{
	private String command;
	private Fingerprint fingerprint = null;
	
	public ManagementCommand(String command){
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Fingerprint getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(Fingerprint fingerprint) {
		this.fingerprint = fingerprint;
	}
}
