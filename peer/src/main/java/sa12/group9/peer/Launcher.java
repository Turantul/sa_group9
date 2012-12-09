package sa12.group9.peer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import sa12.group9.common.util.Constants;

public class Launcher
{
    public static String USERNAME;
    public static String PASSWORD;
    public static int LISTENINGPORT;
    public static int KEEPALIVEPORT;
    public static int MANAGEMENTPORT;
    
    public static void main(String[] args)
    {
        if (args.length != 5)
        {
            System.out.println("Wrong usage: <username> <password> <listeningPort> <keepAlivePort> <managementPort>");
        }
        else
        {
            USERNAME = args[0];
            PASSWORD = args[1];
            try
            {
                LISTENINGPORT = Integer.parseInt(args[2]);
                KEEPALIVEPORT = Integer.parseInt(args[3]);
                MANAGEMENTPORT = Integer.parseInt(args[4]);
                new ClassPathXmlApplicationContext(Constants.SPRINGBEANS).getBean("kernel");
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please enter a number as the listeningPort, keepAlivePort and managementPort!");
            }
        }
    }
}
