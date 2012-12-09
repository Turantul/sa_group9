package sa12.group9.peer.service.thread;

import java.net.Socket;

public class RequestHandler extends Thread
{
    private Socket socket;
    
    public RequestHandler(Socket socket)
    {
        this.socket = socket;
    }
    
    @Override
    public void run()
    {
        // TODO: listen and respond to requests
    }
}
