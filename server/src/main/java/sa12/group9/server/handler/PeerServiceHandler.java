package sa12.group9.server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import sa12.group9.server.util.PropertiesHelper;

public class PeerServiceHandler implements IPeerServiceHandler
{
    private static Log log = LogFactory.getLog(PeerServiceHandler.class);

    private IUserDAO userdao = MongoUserDAO.getInstance();
    private IPeerDAO peerdao = MongoPeerDAO.getInstance();

    private int minStepSize = 2;
    private int maxStepSize = 20;

    public PeerServiceHandler()
    {
        minStepSize = 2;
        maxStepSize = 20;

        try
        {
            minStepSize = Integer.parseInt(PropertiesHelper.getProperty("minStepSize"));
            maxStepSize = Integer.parseInt(PropertiesHelper.getProperty("maxStepSize"));
        }
        catch (IOException ex)
        {
            log.info("Failed to read properties file");
        }
    }

    @Override
    public PeerList getRandomPeerList(LoginRequest request)
    {
        if (authenticate(request))
        {
            long count = peerdao.getCountOfPeers();

            int stepSize = (int) (count / 100);
            if (stepSize < minStepSize)
            {
                stepSize = minStepSize;
            }
            else if (stepSize > maxStepSize)
            {
                stepSize = maxStepSize;
            }

            List<PeerEndpoint> randomPeersSelection = new ArrayList<PeerEndpoint>();
            List<PeerEndpoint> allPeers = peerdao.getAllPeers();

            Random random = new Random();
            int randomlyChosenPeersCount = 0;

            // iterate as long as either the wanted number is reached or the
            // maximum
            // number of peers in database return
            while ((randomlyChosenPeersCount < stepSize) && (randomlyChosenPeersCount < allPeers.size()))
            {
                PeerEndpoint randomPeer = allPeers.get(random.nextInt(allPeers.size()));
                if (!randomPeersSelection.contains(randomPeer))
                {
                    randomPeersSelection.add(randomPeer);
                    randomlyChosenPeersCount++;
                }
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
    public boolean markAsAlive(IsAliveNotification request, String remoteAddress)
    {
        if (authenticate(request))
        {
            PeerEndpoint peer = peerdao.getPeer(remoteAddress, request.getListeningPort(), request.getKeepAlivePort());

            if (peer == null)
            {
                System.out.println("successfully logged in as " + request.getUsername().toString() + "");

                peer = new PeerEndpoint();
                peer.setAddress(remoteAddress);
                peer.setUuid(UUID.randomUUID().toString());
                peer.setKeepAlivePort(request.getKeepAlivePort());
                peer.setListeningPort(request.getListeningPort());
            }

            peer.setLastKeepAlive(new Date());
            peerdao.storePeer(peer);

            return true;
        }
        return false;
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
