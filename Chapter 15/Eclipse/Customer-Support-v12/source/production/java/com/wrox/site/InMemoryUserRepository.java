package com.wrox.site;

import org.springframework.stereotype.Repository;

import java.util.Hashtable;
import java.util.Map;

@Repository
public class InMemoryUserRepository implements UserRepository
{
    private final Map<String, String> userDatabase = new Hashtable<>();

    public InMemoryUserRepository()
    {
        this.userDatabase.put("Nicholas", "password");
        this.userDatabase.put("Sarah", "drowssap");
        this.userDatabase.put("Mike", "wordpass");
        this.userDatabase.put("John", "green");
    }

    @Override
    public String getPasswordForUser(String username)
    {
        return this.userDatabase.get(username);
    }
}
