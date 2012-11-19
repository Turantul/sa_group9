package sa12.group9.client.service;

import java.io.IOException;

import sa12.group9.client.gui.action.MainAction;
import sa12.group9.common.beans.PeerEndpoint;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public interface IPeerHandler
{
    void openListeningSocket(final MainAction mainAction, int seconds) throws IOException;

    void sendSearchRequest(PeerEndpoint peer, Fingerprint finger) throws IOException;
}
