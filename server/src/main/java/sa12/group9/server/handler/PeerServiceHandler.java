package sa12.group9.server.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import sa12.group9.common.beans.IsAliveNotification;
import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.PeerList;
import sa12.group9.common.beans.User;

import sa12.group9.common.util.Encrypter;

import sa12.group9.server.dao.IPeerDAO;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoPeerDAO;
import sa12.group9.server.dao.MongoUserDAO;

public class PeerServiceHandler implements IPeerServiceHandler
{
    private IUserDAO userdao = MongoUserDAO.getInstance();
    private IPeerDAO peerdao = MongoPeerDAO.getInstance();

    @Override
    public boolean verifyLogin(IsAliveNotification request, String remoteAddress)
    {
        if (authenticate(request))
        {
            System.out.println("successfully logged in as " + request.getUsername().toString() + "");

            PeerEndpoint pdto = new PeerEndpoint();
            pdto.setAddress(remoteAddress);
            pdto.setUuid(UUID.randomUUID().toString());
            pdto.setKeepAlivePort(request.getKeepAlivePort());
            pdto.setListeningPort(request.getListeningPort());
            pdto.setLastKeepAlive(new Date());

            peerdao.storePeer(pdto);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public PeerList getRandomPeerList(LoginRequest request, int numberOfWantedPeers)
    {
        if (authenticate(request))
        {
            PeerList allPeers = new PeerList();
            List<PeerEndpoint> randomPeersSelection = new ArrayList<PeerEndpoint>();
            allPeers.setPeers(peerdao.getAllPeers());

            Random random = new Random();
            int randomlyChosenPeersCount = 0;

            // iterate as long as either the wanted number is reached or the maximum
            // number of peers in database return
            // TODO: jeder peer soll nach möglichkeit nur einmal ausgewählt werden
            while ((randomlyChosenPeersCount < numberOfWantedPeers) && (randomlyChosenPeersCount < allPeers.getPeers().size()))
            {
                randomPeersSelection.add(allPeers.getPeers().get(random.nextInt(allPeers.getPeers().size())));
                randomlyChosenPeersCount++;
            }

            PeerList returnPeerList = new PeerList();
            returnPeerList.setPeers(randomPeersSelection);

            return returnPeerList;
        }
        else
        {
            return null;
        }

    }

    @Override
    public void markAsAlive(IsAliveNotification request, String remoteAddress)
    {
        if (authenticate(request))
        {
            PeerEndpoint peer = peerdao.getPeer(remoteAddress, request.getListeningPort(), request.getKeepAlivePort());
            peer.setLastKeepAlive(new Date());
            peerdao.storePeer(peer);
        }
    }

    private boolean authenticate(LoginRequest request)
    {
        User fetcheduser = userdao.searchUser(request.getUsername());

        if (fetcheduser == null)
        {
            return false;
        }
        return request.getUsername().equals(fetcheduser.getUsername()) && Encrypter.encryptString(request.getPassword()).equals(fetcheduser.getPassword());
    }
}
