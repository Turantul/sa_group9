package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.CoinHistory;

public interface ICoinHistoryDAO
{
    /**
     * Stores the CoinHistory in the database
     * 
     * @param coinhistory information about the CoinHistory
     */
    void storeCoinHistory(CoinHistory coinhistory);

    /**
     * Stores a List of PeerEndpoints in the database
     * 
     * @param peers a List of PeerEndpoints (information about the peer)
     */
    void storeCoinHistory(List<CoinHistory> coinhistories);

    /**
     * Returns all CoinHistories from the database
     * 
     * @return list of CoinHistories
     */
    List<CoinHistory> getAllCoinHistories();

    /**
     * Searches a cointhistory by given Username
     * 
     * @param username of the
     * @return the peer endpoint
     */
    CoinHistory searchCoinHistoryByUsername(String username);
}
