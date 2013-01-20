package sa12.group9.client.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sa12.group9.client.service.ICallback;
import sa12.group9.client.service.IPeerHandler;
import sa12.group9.client.service.IServerHandler;
import sa12.group9.common.beans.FoundInformation;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.SearchIssueResponse;
import sa12.group9.common.media.IFingerprintService;
import sa12.group9.common.util.Constants;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class Console implements ICallback
{
    private List<FoundInformation> songs;
    private String username;
    private String password;
    private String location;

    private IFingerprintService fingerprintService;
    private IServerHandler serverHandler;
    private IPeerHandler peerHandler;

    public static void main(String[] args)
    {
        if (args.length != 3)
        {
            System.out.println("Wrong usage: <username> <password> <locationOfFile>");
        }
        else
        {
            new Console(args[0], args[1], args[2]);
        }
    }

    public Console(String username, String password, String location)
    {
        this.username = username;
        this.password = password;
        this.location = location;

        ApplicationContext ctx = new ClassPathXmlApplicationContext(Constants.SPRINGBEANS);
        fingerprintService = (IFingerprintService) ctx.getBean("fingerprintService");
        serverHandler = (IServerHandler) ctx.getBean("serverHandler");
        peerHandler = (IPeerHandler) ctx.getBean("peerHandler");

        calculate();
    }

    private void calculate()
    {
        try
        {
            boolean success = serverHandler.loginAtServer(username, password);

            if (success)
            {
                try
                {
                    System.out.println("Calculating fingerprint...");
                    Fingerprint finger = null;
                    try
                    {
                        finger = fingerprintService.generateFingerprint(location);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.out.println("The fingerprint is too small. Please try a longer one.");
                        return;
                    }
                    String id = UUID.randomUUID().toString();

                    SearchIssueResponse response = serverHandler.generateSearchRequest(username, password, id, finger.hashCode());
                    if (response.getErrorMsg() != null && !response.getErrorMsg().equals(""))
                    {
                        System.out.println("There was a problem creating your search!\nReason: " + response.getErrorMsg());
                    }
                    else
                    {
                        songs = new ArrayList<FoundInformation>();

                        try
                        {
                            peerHandler.openListeningSocket(this);

                            int i = 0;
                            for (PeerEndpoint peer : response.getPeers().getPeers())
                            {
                                try
                                {
                                    peerHandler.sendSearchRequest(id, peer, finger, response);
                                    i++;
                                }
                                catch (IOException e)
                                {
                                    System.out.println("Peer at " + peer.getAddress() + " at port " + peer.getListeningPort() + " could not be reached.");
                                }
                            }

                            if (i == 0)
                            {
                                System.out.println("No peer could be found to accept the search request.\nPlease try again at a later point in time.");
                            }
                            else
                            {
                                System.out.println("Waiting " + response.getSecondsToWait() + " seconds for responses...");
                                Thread.sleep(response.getSecondsToWait() * 1000);
                                peerHandler.shutdown();

                                if (songs.size() == 0)
                                {
                                    System.out.println("Unfortunately, your search request has yielded no answers.\nYou can retry your request if you want.");
                                }
                                else
                                {
                                    if (songs.size() > 1)
                                    {
                                        Collections.sort(songs, new Comparator<FoundInformation>()
                                        {
                                            @Override
                                            public int compare(FoundInformation o1, FoundInformation o2)
                                            {
                                                if (o1.getMatch() == o2.getMatch())
                                                {
                                                    return 0;
                                                }
                                                else if (o1.getMatch() < o2.getMatch())
                                                {
                                                    return 1;
                                                }
                                                return -1;
                                            }
                                        });
                                    }

                                    serverHandler.notifySuccess(username, password, id, songs.get(0));

                                    System.out.println("Your search was successful. The song was found by " + songs.get(0).getPeerUsername() + " with a match of "
                                            + songs.get(0).getMatch());
                                    System.out.println("Title: " + songs.get(0).getTitle());
                                    System.out.println("Interpret: " + songs.get(0).getInterpret());
                                    System.out.println("Album: " + (songs.get(0).getAlbum() == null || songs.get(0).getAlbum().equals("") ? "-" : songs.get(0).getAlbum()));
                                    System.out.println("Genre: " + (songs.get(0).getGenre() == null || songs.get(0).getGenre().equals("") ? "-" : songs.get(0).getGenre()));
                                    System.out.println("Length: " + (songs.get(0).getLength() == 0 ? "-" : (songs.get(0).getLength() / 60 + ":" + songs.get(0).getLength() % 60)));

                                    System.out.println("Altogether " + songs.size() + " answer(s) received");
                                }
                            }
                        }
                        catch (IOException e)
                        {
                            System.out.println("Could not open listening socket.");
                        }
                        catch (InterruptedException e)
                        {
                            System.out.println("An unknown error occured.\nPlease try again.");
                        }
                    }
                }
                catch (IOException e)
                {
                    System.out.println("The audio file could not be found!");
                }
            }
            else
            {
                System.out.println("Unknown username/password combination!");
            }
        }
        catch (Exception e)
        {
            System.out.println("The server could not be reached.\nPlease try again later.");
        }
    }

    @Override
    public synchronized void receivingCallback(FoundInformation information)
    {
        if (songs != null)
        {
            System.out.println("Response received from: " + information.getPeerUsername());
            songs.add(information);
        }
    }
}
