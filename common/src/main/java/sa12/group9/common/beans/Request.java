package sa12.group9.common.beans;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "requests")
public class Request
{
    @Id
    private String id;

    private String username;
    private Date issuedate;
    private Date finisheddate;
    private String status;
    private String foundbyuser;
    private String interpret;
    private String title;

    public String getInterpret()
    {
        return interpret;
    }

    public void setInterpret(String interpret)
    {
        this.interpret = interpret;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getFoundbyuser()
    {
        return foundbyuser;
    }

    public void setFoundbyuser(String foundbyuser)
    {
        this.foundbyuser = foundbyuser;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getIssuedate()
    {
        return issuedate;
    }

    public void setIssuedate(Date issuedate)
    {
        this.issuedate = issuedate;
    }

    public Date getFinisheddate()
    {
        return finisheddate;
    }

    public void setFinisheddate(Date finisheddate)
    {
        this.finisheddate = finisheddate;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Request [id=" + id + ", username=" + username + ", issuedate=" + issuedate + ", finisheddate=" + finisheddate + ", status=" + status + ", foundbyuser="
                + foundbyuser + ", interpret=" + interpret + ", title=" + title + "]";
    }

}
