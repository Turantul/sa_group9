package sa12.group9.client.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import sa12.group9.common.beans.FoundInformation;
import sa12.group9.common.beans.P2PSearchRequest;
import sa12.group9.common.beans.PeerEndpoint;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class PeerHandler implements IPeerHandler
{
	private int listeningPort;
	private ServerSocket serverSocket;

	public void openListeningSocket(final ICallback mainAction) throws IOException
	{
		serverSocket = new ServerSocket(listeningPort);

		try
		{
			while (true)
			{
				try
				{
					Socket socket = serverSocket.accept();

					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					FoundInformation found = (FoundInformation) in.readObject();

					mainAction.receivingCallback(found);

					socket.close();
				}
				catch (ClassNotFoundException e)
				{
					System.out.println("Error reading from TCP socket!");
				}
			}
		}
		catch (IOException e)
		{
			if (!serverSocket.isClosed())
			{
				System.out.println("Error reading from TCP socket!");
			}
		}
		
		serverSocket.close();
	}

	public void sendSearchRequest(String id, PeerEndpoint peer, Fingerprint fingerprint, int ttl, int maxPeersForForwarding) throws IOException
	{
		Socket socket = new Socket(peer.getAddress(), peer.getListeningPort());

		P2PSearchRequest request = new P2PSearchRequest();
		request.setId(id);
		request.setFingerprint(fingerprint);
		request.setRequesterAddress(InetAddress.getLocalHost());
		request.setRequesterPort(listeningPort);
		request.setTtl(ttl);
		request.setMaxPeersForForwarding(maxPeersForForwarding);

		ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
		socketout.writeObject(request);
		socketout.close();

		socket.close();
	}

	public void setListeningPort(int listeningPort)
	{
		this.listeningPort = listeningPort;
	}

	public void shutdown()
	{
		if (serverSocket != null && !serverSocket.isClosed())
		{
			try
			{
				serverSocket.close();
			}
			catch (IOException e)
			{}
		}
	}
}
