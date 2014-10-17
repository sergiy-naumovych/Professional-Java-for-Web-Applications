package com.wrox.site;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface AccountService
{
    @NotNull
    public List<Account> getAllAccounts();
    public Account getAccount(long id);
    public Account saveAccount(
            @NotNull(message = "{validate.accountService.saveAccount.account}")
            @Valid Account account
    );
    public void deleteAccount(long id);
}
