package sa12.group9.client.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sa12.group9.common.beans.FoundInformation;
import sa12.group9.common.beans.PeerEndpoint;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class PeerHandler implements IPeerHandler
{
    private int listeningPort;

    public void openListeningSocket(final ICallback mainAction, int seconds) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(listeningPort);

        long start = System.currentTimeMillis();
        do
        {
            try
            {
                Socket socket = serverSocket.accept();

                new Thread()
                {
                    @Override
                    public void run()
                    {
                        // TODO: process response

                        FoundInformation information = new FoundInformation();
                        mainAction.receivingCallback(information);
                    }
                }.start();

                socket.close();
            }
            catch (IOException e)
            {
                if (!serverSocket.isClosed())
                {
                    System.out.println("Error reading from TCP socket!");
                }
            }
        } while (System.currentTimeMillis() - start < seconds * 1000);

        serverSocket.close();
    }

    public void sendSearchRequest(PeerEndpoint peer, Fingerprint fingerprint) throws IOException
    {
        Socket socket = new Socket(peer.getAddress(), peer.getPort());

        // TODO: send out request with fingerprint, client callback address and
        // ttl

        socket.close();
    }

    public void setListeningPort(int listeningPort)
    {
        this.listeningPort = listeningPort;
    }
}
