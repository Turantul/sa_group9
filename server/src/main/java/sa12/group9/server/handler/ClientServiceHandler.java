package sa12.group9.server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.PeerList;
import sa12.group9.common.beans.Request;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.SearchIssueResponse;
import sa12.group9.common.beans.SuccessRequest;
import sa12.group9.common.beans.User;
import sa12.group9.common.util.Encrypter;
import sa12.group9.server.dao.IPeerDAO;
import sa12.group9.server.dao.IRequestDAO;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoPeerDAO;
import sa12.group9.server.dao.MongoRequestDAO;
import sa12.group9.server.dao.MongoUserDAO;
import sa12.group9.server.util.PropertiesHelper;

public class ClientServiceHandler implements IClientServiceHandler
{
    private static Log log = LogFactory.getLog(ClientServiceHandler.class);
    
    private IUserDAO userdao = MongoUserDAO.getInstance();
    private IPeerDAO peerdao = MongoPeerDAO.getInstance();

    private IRequestDAO requestdao = MongoRequestDAO.getInstance();

    
    private double coveragePercentage;
    private int minStepSize;
    private int maxStepSize;
    private int durationPerLevel;
    
    public ClientServiceHandler()
    {
        coveragePercentage = 0.5;
        minStepSize = 2;
        maxStepSize = 20;
        durationPerLevel = 3;
        
        try
        {
            coveragePercentage = Double.parseDouble(PropertiesHelper.getProperty("coveragePercentage"));
            minStepSize = Integer.parseInt(PropertiesHelper.getProperty("minStepSize"));
            maxStepSize = Integer.parseInt(PropertiesHelper.getProperty("maxStepSize"));
            durationPerLevel = Integer.parseInt(PropertiesHelper.getProperty("durationPerLevel"));
        }
        catch (IOException ex)
        {
            log.info("Failed to read properties file");
        }
    }

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
                
                if (count >= minStepSize)
                {
                    int stepSize = (int) (count / 100);
                    if (stepSize < minStepSize)
                    {
                        stepSize = minStepSize;
                    }
                    else if (stepSize > maxStepSize)
                    {
                        stepSize = maxStepSize;
                    }

                    int ttl = new Double(Math.ceil(Math.log(count * coveragePercentage) / Math.log(stepSize))).intValue();
                    int secondsToWait = (ttl + 1) * durationPerLevel;

                    response.setPeers(getRandomPeerList(stepSize));
                    response.setMaxPeersForForwarding(stepSize);
                    response.setTtl(ttl);
                    response.setSecondsToWait(secondsToWait);
                    
                    log.info("Created new request for " + request.getUsername() + " with a stepsize of " + stepSize + ", TTL of " + ttl + " and " + secondsToWait + " seconds to wait.");

                    //set request log parameters
                    Request requestToLog = new Request();
                    
                    requestToLog.setId(request.getId());
                    requestToLog.setUsername(request.getUsername());
                    requestToLog.setIssuedate(new Date());
                    requestToLog.setStatus("pending");
                    requestToLog.setFinisheddate(new Date(System.currentTimeMillis()+(secondsToWait*1000)));
                    
                    try{
                
                    	//create request in db
                    	requestdao.storeRequest(requestToLog);
                        
                    }catch (Exception e) {
						e.printStackTrace();
						log.info("failed to save Request for user " + request.getUsername() + ", due to error with MongoRequestDAO");
					}
                    
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
            
        	//update the coins of client that issued the request!
        	try {
            	User user = userdao.searchUser(request.getUsername());
                user.setCoins(user.getCoins() - 1);
                userdao.updateUser(user);
                
                
			} catch (Exception e) {
				e.printStackTrace();
				log.info("failed to save user " + request.getUsername() + ", due to error with MongouserDAO");
			}
        	
          	//update the coins of peer that found the song!
        	try {
            	User user = userdao.searchUser(request.getInformation().getPeerUsername());
                user.setCoins(user.getCoins() + 3);
                userdao.updateUser(user);
                               
                
			} catch (Exception e) {
				e.printStackTrace();
				log.info("failed to save user " + request.getInformation().getPeerUsername() + ", due to error with MongouserDAO");
			}
            
        	//log in the database
            try {
				
            	Request requestToLog = requestdao.searchRequestById(request.getId());

            	requestToLog.setFinisheddate(new Date());
            	requestToLog.setStatus("finished");
            	requestToLog.setFoundbyuser(request.getInformation().getPeerUsername());
            	requestToLog.setInterpret(request.getInformation().getInterpret());
            	requestToLog.setTitle(request.getInformation().getTitle());

            	requestdao.updateRequest(requestToLog);
            	
			} catch (Exception e) {
				e.printStackTrace();
				log.info("failed to save request for user: " + request.getUsername() + ", due to error with MongoRequestDAO");
			}
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
        
		//  System.out.println("requestusername:" + request.getUsername());
		//  System.out.println("requestpassword:" + request.getPassword());
		//        
		//  System.out.println("fetchedusername:" + fetcheduser.getUsername());
		//  System.out.println("fetchedpassword:" + fetcheduser.getPassword());        
        

        //do not encrypt password for matching
        //return request.getUsername().equals(fetcheduser.getUsername()) && request.getPassword().equals(fetcheduser.getPassword());    
        
        //encrypt password of request for matching
        return request.getUsername().equals(fetcheduser.getUsername()) && Encrypter.encryptString(request.getPassword()).equals(fetcheduser.getPassword());
    }
}
