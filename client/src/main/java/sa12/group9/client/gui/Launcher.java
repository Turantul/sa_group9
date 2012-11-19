package sa12.group9.client.gui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sa12.group9.common.util.Constants;

public class Launcher
{
    public static void main(String[] args)
    {
        ApplicationContext xbf = new ClassPathXmlApplicationContext(Constants.SPRINGBEANS);
        xbf.getBean("mainAction");
    }
}