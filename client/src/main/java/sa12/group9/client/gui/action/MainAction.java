package sa12.group9.client.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.client.gui.misc.ActionCommands;
import sa12.group9.client.gui.misc.AudioFilter;
import sa12.group9.client.gui.swing.MainFrame;
import sa12.group9.client.gui.swing.panel.CalculatingPanel;
import sa12.group9.client.gui.swing.panel.IssuingSearchRequestPanel;
import sa12.group9.client.gui.swing.panel.MainPanel;
import sa12.group9.client.service.ServiceProvider;
import sa12.group9.common.beans.SearchResponse;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class MainAction implements ActionListener
{
    private static Log log = LogFactory.getLog(MainAction.class);

    private String userId;
    
    private MainFrame frame;
    private File f;
    private Thread processing;

    public MainAction()
    {
        log.debug("Creating MainAction");

        frame = new MainFrame(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals(ActionCommands.EXIT))
        {
            System.exit(0);
        }
        else if (e.getActionCommand().equals(ActionCommands.LOGIN))
        {
            new LoginAction(this);
        }
        else if (e.getActionCommand().equals(ActionCommands.CHOOSEFILE))
        {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new AudioFilter());
            fc.setAcceptAllFileFilterUsed(false);
            int returnval = fc.showOpenDialog(frame);
            if (returnval == JFileChooser.APPROVE_OPTION)
            {
                f = fc.getSelectedFile();
                frame.swapPanel(new MainPanel(this, f.getName()));
            }
        }
        else if (e.getActionCommand().equals(ActionCommands.SEARCH))
        {
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
            public void run()
            {
                try
                {
                    log.info("Calculating fingerprint");
                    frame.swapPanel(new CalculatingPanel(MainAction.this));
                    Fingerprint finger = ServiceProvider.generateFingerprint(f.getAbsolutePath());
    
                    log.info("Issuing server request");
                    frame.swapPanel(new IssuingSearchRequestPanel(MainAction.this));
                    SearchResponse response = ServiceProvider.generateSearchRequest(userId, finger.hashCode());
                    if (response.getErrorMsg() != null && !response.getErrorMsg().equals(""))
                    {
                        log.info("Request could not be issued because: " + response.getErrorMsg());
                        frame.showError("There was a problem creating your search!\nReason: " + response.getErrorMsg(), "Error issuing search");
                        frame.swapPanel(new MainPanel(MainAction.this, f.getName()));
                    }
                    else
                    {
                        // TODO: send to peers
                        log.info("Sending request to peers");
        
                        // TODO: open server for listening
                        log.info("Waiting for responses...");
                    }
                }
                catch (IOException e)
                {
                    log.error("There was an error processing the audio file!");
                    
                    frame.showError("There was an error processing the audio file!", "Error processing file");
                    frame.swapPanel(new MainPanel(MainAction.this, f.getName()));
                }
            }
        };
    }
}