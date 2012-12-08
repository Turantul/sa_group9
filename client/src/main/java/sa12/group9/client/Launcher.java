package sa12.group9.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import sa12.group9.common.util.Constants;

public class Launcher
{
    public static void main(String[] args)
    {
        new ClassPathXmlApplicationContext(Constants.SPRINGBEANS).getBean("mainAction");
    }
}