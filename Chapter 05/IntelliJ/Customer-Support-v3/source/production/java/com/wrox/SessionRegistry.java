package com.wrox;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public final class SessionRegistry
{
    private static final Map<String, HttpSession> SESSIONS = new Hashtable<>();

    public static void addSession(HttpSession session)
    {
        SESSIONS.put(session.getId(), session);
    }

    public static void updateSessionId(HttpSession session, String oldSessionId)
    {
        synchronized(SESSIONS)
        {
            SESSIONS.remove(oldSessionId);
            addSession(session);
        }
    }

    public static void removeSession(HttpSession session)
    {
        SESSIONS.remove(session.getId());
    }

    public static List<HttpSession> getAllSessions()
    {
        return new ArrayList<>(SESSIONS.values());
    }

    public static int getNumberOfSessions()
    {
        return SESSIONS.size();
    }

    private SessionRegistry()
    {

    }
}
