package sa12.group9.peer.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RequestManager implements IRequestManager
{
    private Map<String, Long> requests;

    public RequestManager()
    {
        requests = Collections.synchronizedMap(new HashMap<String, Long>());
    }

    @Override
    public void addRequest(String id, Long time)
    {
        synchronized (requests)
        {
            requests.put(id, time);
        }
    }

    @Override
    public boolean wasAlreadyHandled(String id)
    {
        synchronized (requests)
        {
            return requests.containsKey(id);
        }
    }

    @Override
    public void removeRequest(String id)
    {
        synchronized (requests)
        {
            if (requests.containsKey(id))
            {
                requests.remove(id);
            }
        }
    }

    @Override
    public Set<String> getRequestSnapshot()
    {
        synchronized (requests)
        {
            return new HashSet<String>(requests.keySet());
        }
    }

    @Override
    public Long getRequestTime(String id)
    {
        synchronized (requests)
        {
            return requests.get(id);
        }
    }
}
