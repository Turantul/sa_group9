package sa12.group9.server.handler;

import java.util.Date;

import sa12.group9.common.beans.FoundInformation;
import sa12.group9.common.beans.Request;
import sa12.group9.common.beans.SuccessRequest;
import sa12.group9.common.beans.User;
import sa12.group9.common.util.Encrypter;
import sa12.group9.server.dao.IRequestDAO;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoRequestDAO;
import sa12.group9.server.dao.MongoUserDAO;

public class ClientServiceHandlerTest
{
    public static void main(String[] args)
    {
        testNotifySuccess();
    }

    public static void testNotifySuccess()
    {
        IUserDAO userdao = MongoUserDAO.getInstance();
        IRequestDAO requestdao = MongoRequestDAO.getInstance();

        // create the user;

        User user = new User();
        user.setCoins(20);
        user.setUsername("bert");

        user.setPassword(Encrypter.encryptString("1234"));

        userdao.storeUser(user);

        // create the peer;

        User peer = new User();
        peer.setCoins(20);
        peer.setUsername("hugo");
        peer.setPassword(Encrypter.encryptString("1234"));

        userdao.storeUser(peer);

        // create the original request

        Request request = new Request();
        request.setId("123");
        request.setIssuedate(new Date());
        request.setUsername("bert");
        request.setStatus("pending");

        requestdao.storeRequest(request);

        // create the successrequest

        SuccessRequest successrequest = new SuccessRequest();
        successrequest.setUsername("bert");
        successrequest.setId("123");
        successrequest.setPassword("1234");

        FoundInformation foundinfo = new FoundInformation();
        foundinfo.setPeerUsername("hugo");
        foundinfo.setInterpret("nirvana");
        foundinfo.setTitle("smells like poo");
        successrequest.setInformation(foundinfo);

        IClientServiceHandler clientservice = new ClientServiceHandler();

        // run notifySuccess

        clientservice.notifySuccess(successrequest);

        // print updated result

        System.out.println(requestdao.searchRequestById("123").toString());
        System.out.println(userdao.searchUser("bert").toString());
        System.out.println(userdao.searchUser("hugo").toString());
    }

}
