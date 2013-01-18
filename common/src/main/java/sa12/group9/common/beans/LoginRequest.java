package sa12.group9.common.beans;

import sa12.group9.common.util.Encrypter;

public class LoginRequest
{
    protected String username;
    protected String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
