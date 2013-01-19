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

                // Default desired coverage
                double coveragePercentage = 0.5;

                try
                {
                    Properties prop = new Properties();
                    prop.load(ClientServiceHandler.class.getClassLoader().getResourceAsStream("config.properties"));
                    coveragePercentage = Double.parseDouble(prop.getProperty("coveragePercentage"));

                }
                catch (IOException ex)
                {}

                int stepSize = (int) (count / 100);
                if (stepSize < 2)
                {
                    stepSize = 2;
                }
                else if (stepSize > 20)
                {
                    stepSize = 20;
                }

                int ttl = (int) (Math.log(count * coveragePercentage) / Math.log(stepSize));
                // TODO: find reasonable duration per level
                int secondsToWait = ttl * 5;

                response.setPeers(getRandomPeerList(stepSize));
                response.setMaxPeersForForwarding(stepSize);
                response.setTtl(ttl);
                response.setSecondsToWait(secondsToWait);

                // TODO: log in the database
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
