package sa12.group9.common.beans;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="requests")
public class Request {
	
	@Id
	private String id;

	private String username;
	private Date issuedate;
	private Date finisheddate;
	private String status;

	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getIssueDate() {
		return issuedate;
	}
	public void setIssueDate(Date issueDate) {
		this.issuedate = issueDate;
	}
	public Date getFinishedDate() {
		return finisheddate;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finisheddate = finishedDate;
	}
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
    

}
