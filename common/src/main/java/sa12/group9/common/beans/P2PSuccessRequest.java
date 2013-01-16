package sa12.group9.common.beans;

import java.io.Serializable;

public class P2PSuccessRequest implements Serializable{
	
	private String id;
	private FoundInformation foundInformation;
	
	public P2PSuccessRequest(){
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FoundInformation getFoundInformation() {
		return foundInformation;
	}

	public void setFoundInformation(FoundInformation foundInformation) {
		this.foundInformation = foundInformation;
	}
	
	

}
