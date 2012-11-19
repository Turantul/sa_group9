package sa12.group9.client.service;

import sa12.group9.common.beans.FoundInformation;
import sa12.group9.common.beans.SearchIssueResponse;

public interface IServerHandler
{
    String loginAtServer(String username, String password);

    SearchIssueResponse generateSearchRequest(String userId, int hash);

    void notifySuccess(String userId, int hash, FoundInformation information);
}
