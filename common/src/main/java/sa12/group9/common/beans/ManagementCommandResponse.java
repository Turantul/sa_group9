package sa12.group9.common.beans;

import java.io.Serializable;

public class ManagementCommandResponse implements Serializable{
	
	private String commandResponse;
	
	public ManagementCommandResponse(String response){
		this.commandResponse = response;
	}

	public String getCommandResponse() {
		return commandResponse;
	}

	public void setCommandResponse(String commandResponse) {
		this.commandResponse = commandResponse;
	}
}
