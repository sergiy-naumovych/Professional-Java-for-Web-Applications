package com.wrox.site;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.function.Consumer;

public interface SessionRegistry
{
    void addSession(HttpSession session);
    void updateSessionId(HttpSession session, String oldSessionId);
    void removeSession(HttpSession session);
    List<HttpSession> getAllSessions();
    int getNumberOfSessions();
    void registerOnRemoveCallback(Consumer<HttpSession> callback);
    void deregisterOnRemoveCallback(Consumer<HttpSession> callback);
}
