package com.wrox.site;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
public class DefaultAccountService implements AccountService
{
    private final Map<Long, Account> accountRepository = new Hashtable<>();

    private volatile long accountIdSequence = 1L;

    @Override
    public List<Account> getAllAccounts()
    {
        List<Account> list = new ArrayList<>(this.accountRepository.values());
        list.sort(
                (a1, a2) -> a1.getId() < a2.getId() ? -1 : 1
        );
        return list;
    }

    @Override
    public Account getAccount(long id)
    {
        return this.accountRepository.get(id);
    }

    @Override
    public Account saveAccount(Account account)
    {
        if(account.getId() < 1)
            account.setId(this.nextAccountId());
        account.setLastModified(Instant.now());
        this.accountRepository.put(account.getId(), account);
        return account;
    }

    @Override
    public void deleteAccount(long id)
    {
        this.accountRepository.remove(id);
    }

    private synchronized long nextAccountId()
    {
        return this.accountIdSequence++;
    }
}
