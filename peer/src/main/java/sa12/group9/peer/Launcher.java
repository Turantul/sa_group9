package sa12.group9.peer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import sa12.group9.common.util.Constants;

public class Launcher
{
    public static String USERNAME;
    public static String PASSWORD;
    
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.out.println("Wrong usage: <username> <password>");
        }
        else
        {
            USERNAME = args[0];
            PASSWORD = args[1];
            new ClassPathXmlApplicationContext(Constants.SPRINGBEANS).getBean("kernel");
        }
    }
}
