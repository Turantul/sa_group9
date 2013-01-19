package sa12.group9.server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.PeerList;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.SearchIssueResponse;
import sa12.group9.common.beans.SuccessRequest;
import sa12.group9.common.beans.User;
import sa12.group9.common.util.Encrypter;
import sa12.group9.server.dao.IPeerDAO;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoPeerDAO;
import sa12.group9.server.dao.MongoUserDAO;

public class ClientServiceHandler implements IClientServiceHandler
{
    private IUserDAO userdao = MongoUserDAO.getInstance();
    private IPeerDAO peerdao = MongoPeerDAO.getInstance();

    @Override
    public boolean verifyLogin(LoginRequest request)
    {
        if (authenticate(request))
        {
            System.out.println("successfully logged in as " + request.getUsername().toString());
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public SearchIssueResponse issueSearchRequest(SearchIssueRequest request)
    {
        SearchIssueResponse response = new SearchIssueResponse();

        if (authenticate(request))
        {
            if (userdao.searchUser(request.getUsername()).getCoins() <= 0)
            {
                response.setErrorMsg("No coins left");
            }
            else
            {
                long count = peerdao.getCountOfPeers();

                if (count < 2)
                {
                    // Default values
                    double coveragePercentage = 0.5;
                    int minStepSize = 2;
                    int maxStepSize = 20;
                    // TODO: find reasonable duration per level
                    int ducationPerLevel = 3;

                    try
                    {
                        Properties prop = new Properties();
                        prop.load(ClientServiceHandler.class.getClassLoader().getResourceAsStream("config.properties"));
                        coveragePercentage = Double.parseDouble(prop.getProperty("coveragePercentage"));
                        minStepSize = Integer.parseInt(prop.getProperty("minStepSize"));
                        maxStepSize = Integer.parseInt(prop.getProperty("maxStepSize"));
                        ducationPerLevel = Integer.parseInt(prop.getProperty("ducationPerLevel"));
                    }
                    catch (IOException ex)
                    {}

                    int stepSize = (int) (count / 100);
                    if (stepSize < minStepSize)
                    {
                        stepSize = minStepSize;
                    }
                    else if (stepSize > maxStepSize)
                    {
                        stepSize = maxStepSize;
                    }

                    int ttl = (int) (Math.log(count * coveragePercentage) / Math.log(stepSize));
                    int secondsToWait = ttl * ducationPerLevel;

                    response.setPeers(getRandomPeerList(stepSize));
                    response.setMaxPeersForForwarding(stepSize);
                    response.setTtl(ttl);
                    response.setSecondsToWait(secondsToWait);

                    // TODO: log in the database
                }
                else
                {
                    response.setErrorMsg("There are not enough peers available");
                }
            }
        }
        else
        {
            response.setErrorMsg("Authentication failed");
        }

        return response;
    }

    @Override
    public void notifySuccess(SuccessRequest request)
    {
        if (authenticate(request))
        {
            User user = userdao.searchUser(request.getUsername());
            user.setCoins(user.getCoins() - 1);

            // TODO: update the coins of the peer who found it!
            // TODO: log in the database
        }
    }

    @Override
    public PeerList getRandomPeerList(int numberOfWantedPeers)
    {
        IPeerDAO peerdao = MongoPeerDAO.getInstance();
        List<PeerEndpoint> allPeers = peerdao.getAllPeers();
        List<PeerEndpoint> randomPeersSelection = new ArrayList<PeerEndpoint>();

        Random random = new Random();
        int randomlyChosenPeersCount = 0;

        // iterate as long as either the wanted number is reached or the maximum
        // number of peers in database return
        while ((randomlyChosenPeersCount < numberOfWantedPeers) && (randomlyChosenPeersCount < allPeers.size()))
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
