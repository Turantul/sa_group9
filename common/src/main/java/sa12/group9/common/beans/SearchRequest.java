package sa12.group9.common.beans;

public class SearchRequest
{
    private String userId;
    private int hash;
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public int getHash()
    {
        return hash;
    }
    
    public void setHash(int hash)
    {
        this.hash = hash;
    }
}
