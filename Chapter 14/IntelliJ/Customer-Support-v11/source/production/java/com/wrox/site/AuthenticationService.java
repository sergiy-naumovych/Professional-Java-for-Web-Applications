package com.wrox.site;

import java.security.Principal;

public interface AuthenticationService
{
    Principal authenticate(String username, String password);
}
