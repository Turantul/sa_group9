package sa12.group9.server.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper
{
    private static Properties prop;

    static
    {
        prop = new Properties();
        try
        {
            prop.load(PropertiesHelper.class.getClassLoader().getResourceAsStream("config.properties"));
        }
        catch (IOException e)
        {
            prop = null;
        }
    }

    public static String getProperty(String property) throws IOException
    {
        if (prop != null)
        {
            return prop.getProperty(property);
        }
        else
        {
            throw new IOException();
        }
    }
}
