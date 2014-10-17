package com.wrox.site;

import com.wrox.site.entities.UserPrincipal;

public interface UserRepository extends GenericRepository<Long, UserPrincipal>
{
    UserPrincipal getByUsername(String username);
}
