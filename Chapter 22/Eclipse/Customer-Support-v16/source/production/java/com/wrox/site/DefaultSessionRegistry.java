package com.wrox.site;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class DefaultSessionRegistry implements SessionRegistry
{
    private final Map<String, HttpSession> sessions = new Hashtable<>();
    private final Set<Consumer<HttpSession>> callbacks = new HashSet<>();
    private final Set<Consumer<HttpSession>> callbacksAddedWhileLocked =
            new HashSet<>();

    @Override
    public void addSession(HttpSession session)
    {
        this.sessions.put(session.getId(), session);
    }

    @Override
    public void updateSessionId(HttpSession session, String oldSessionId)
    {
        synchronized(this.sessions)
        {
            this.sessions.remove(oldSessionId);
            addSession(session);
        }
    }

    @Override
    public void removeSession(HttpSession session)
    {
        this.sessions.remove(session.getId());
        synchronized(this.callbacks)
        {
            this.callbacksAddedWhileLocked.clear();
            this.callbacks.forEach(c -> c.accept(session));
            try {
                this.callbacksAddedWhileLocked.forEach(c -> c.accept(session));
            } catch(ConcurrentModificationException ignore) { }
        }
    }

    @Override
    public List<HttpSession> getAllSessions()
    {
        return new ArrayList<>(this.sessions.values());
    }

    @Override
    public int getNumberOfSessions()
    {
        return this.sessions.size();
    }

    @Override
    public void registerOnRemoveCallback(Consumer<HttpSession> callback)
    {
        this.callbacksAddedWhileLocked.add(callback);
        synchronized(this.callbacks)
        {
            this.callbacks.add(callback);
        }
    }

    @Override
    public void deregisterOnRemoveCallback(Consumer<HttpSession> callback)
    {
        synchronized(this.callbacks)
        {
            this.callbacks.remove(callback);
        }
    }
}
