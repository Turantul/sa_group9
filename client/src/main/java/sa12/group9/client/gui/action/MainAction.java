package sa12.group9.client.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.client.gui.misc.ActionCommands;
import sa12.group9.client.gui.misc.AudioFilter;
import sa12.group9.client.gui.swing.MainFrame;
import sa12.group9.client.gui.swing.panel.CalculatingPanel;
import sa12.group9.client.gui.swing.panel.IssuingSearchRequestPanel;
import sa12.group9.client.gui.swing.panel.MainPanel;
import sa12.group9.client.gui.swing.panel.ResultPanel;
import sa12.group9.client.service.ServiceProvider;
import sa12.group9.common.beans.FoundInformation;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.SearchIssueResponse;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class MainAction implements ActionListener
{
    private static Log log = LogFactory.getLog(MainAction.class);

    private String userId;
    private File f;
    private List<FoundInformation> songs;

    private MainFrame frame;
    private Thread processing;
    
    private ServiceProvider provider;

    public MainAction()
    {
        log.debug("Creating MainAction");

        frame = new MainFrame(this);
    }
    
    public void setProvider(ServiceProvider provider)
    {
        this.provider = provider;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand())
        {
            case ActionCommands.EXIT:
                System.exit(0);
                break;

            case ActionCommands.LOGIN:
                new LoginAction(this, provider);
                break;

            case ActionCommands.CHOOSEFILE:
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new AudioFilter());
                fc.setAcceptAllFileFilterUsed(false);
                int returnval = fc.showOpenDialog(frame);
                if (returnval == JFileChooser.APPROVE_OPTION)
                {
                    f = fc.getSelectedFile();
                    frame.swapPanel(new MainPanel(this, f.getName()));
                }
                break;

            case ActionCommands.SEARCH:
                if (f != null)
                {
                    if (processing == null || !processing.isAlive())
                    {
                        processing = createSearchThread();
                        processing.start();
                    }
                    else
                    {
                        frame.showError("You already issued a song search request.\nWait until it is finished!", "Already searching");
                    }
                }
                else
                {
                    frame.showError("You must select an audio file first!", "No file selected");
                }
                break;
            case ActionCommands.NEW:
                frame.swapPanel(new MainPanel(this));
                break;
        }
    }

    public void loginSuccessful(String id)
    {
        log.info("User logged in with ID: " + id);

        userId = id;
        frame.swapPanel(new MainPanel(this));
    }

    private Thread createSearchThread()
    {
        return new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    log.info("Calculating fingerprint");
                    frame.swapPanel(new CalculatingPanel(MainAction.this));
                    Fingerprint finger = provider.generateFingerprint(f.getAbsolutePath());

                    log.info("Issuing server request");
                    frame.swapPanel(new IssuingSearchRequestPanel(MainAction.this));
                    SearchIssueResponse response = provider.generateSearchRequest(userId, finger.hashCode());
                    if (response.getErrorMsg() != null && !response.getErrorMsg().equals(""))
                    {
                        log.info("Request could not be issued because: " + response.getErrorMsg());
                        frame.showError("There was a problem creating your search!\nReason: " + response.getErrorMsg(), "Error issuing search");
                        resetPanel();
                    }
                    else
                    {
                        log.info("Waiting for responses...");
                        songs = new ArrayList<FoundInformation>();

                        try
                        {
                            provider.openListeningSocket(MainAction.this, 20);

                            log.info("Sending request to peers");
                            int i = 0;
                            for (PeerEndpoint peer : response.getPeers())
                            {
                                try
                                {
                                    provider.sendSearchRequest(peer, finger);
                                    log.info("Request sent to peer at " + peer.getAddress() + " at port " + peer.getPort());
                                    i++;
                                }
                                catch (IOException e)
                                {
                                    log.info("Peer at " + peer.getAddress() + " at port " + peer.getPort() + " could not be reached");
                                }
                            }

                            if (i == 0)
                            {
                                log.info("No peer could be reached at all");
                                frame.showError("No peer could be found to accept the search request.\nPlease try again at a later point in time.", "No peer available");
                                resetPanel();
                            }
                            else
                            {
                                Thread.sleep(20000);

                                if (songs.size() == 0)
                                {
                                    log.info("No answers received.");
                                    frame.showError("Unfortunately, your search request has yielded no answers.\nYou can retry your request if you want.", "No answers");
                                    resetPanel();
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

                                    provider.notifySuccess(userId, finger.hashCode(), songs.get(0));

                                    frame.swapPanel(new ResultPanel(MainAction.this, songs.get(0)));
                                }
                            }
                        }
                        catch (IOException e)
                        {
                            log.error("Could not open listening socket");
                            frame.showError("Could not open listening socket.", "Listening socket");
                            resetPanel();
                        }
                        catch (InterruptedException e)
                        {
                            log.error("Thread got interrupted");
                            frame.showError("An unknown error occured.\nPlease try again.", "Unknown error");
                            resetPanel();
                        }
                    }
                }
                catch (IOException e)
                {
                    log.error("There was an error processing the audio file!");

                    frame.showError("There was an error processing the audio file!", "Error processing file");
                    resetPanel();
                }
            }
        };
    }

    private void resetPanel()
    {
        frame.swapPanel(new MainPanel(MainAction.this, f.getName()));
    }

    public synchronized void receivingCallback(FoundInformation information)
    {
        if (songs != null)
        {
            songs.add(information);
        }
    }
}