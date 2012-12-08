package sa12.group9.common.beans;

public class SearchIssueRequest extends LoginRequest
{
    protected String id;
    protected int hash;

    public int getHash()
    {
        return hash;
    }

    public void setHash(int hash)
    {
        this.hash = hash;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
