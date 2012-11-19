package sa12.group9.client.service;

import sa12.group9.common.beans.FoundInformation;

/**
 * Interface to provide a callback function
 */
public interface ICallback
{
    /**
     * Callback function for receiving a found information
     * 
     * @param information of the found song
     */
    void receivingCallback(FoundInformation information);
}