package com.wrox.site;

import com.wrox.config.annotation.RestEndpoint;
import com.wrox.site.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@RestEndpoint
public class AccountRestEndpoint
{
    @Inject AccountService accountService;

    @RequestMapping(value = "account", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> discover()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "OPTIONS,HEAD,GET,POST");
        return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "account/{id}", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> discover(@PathVariable("id") long id)
    {
        if(this.accountService.getAccount(id) == null)
            throw new ResourceNotFoundException();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "OPTIONS,HEAD,GET,PUT,DELETE");
        return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "account", method = RequestMethod.GET)
    @ResponseBody @ResponseStatus(HttpStatus.OK)
    public AccountList read()
    {
        AccountList list = new AccountList();
        list.setValue(this.accountService.getAllAccounts());
        return list;
    }

    @RequestMapping(value = "account/{id}", method = RequestMethod.GET)
    @ResponseBody @ResponseStatus(HttpStatus.OK)
    public Account read(@PathVariable("id") long id)
    {
        Account account = this.accountService.getAccount(id);
        if(account == null)
            throw new ResourceNotFoundException();
        return account;
    }

    @RequestMapping(value = "account", method = RequestMethod.POST)
    public ResponseEntity<Account> create(@RequestBody AccountForm form)
    {
        Account account = new Account();
        account.setName(form.getName());
        account.setBillingAddress(form.getBillingAddress());
        account.setShippingAddress(form.getShippingAddress());
        account.setPhoneNumber(form.getPhoneNumber());
        account = this.accountService.saveAccount(account);

        String uri = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/account/{id}").buildAndExpand(account.getId()).toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(account, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "account/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") long id, @RequestBody AccountForm form)
    {
        Account account = this.accountService.getAccount(id);
        if(account == null)
            throw new ResourceNotFoundException();
        account.setName(form.getName());
        account.setBillingAddress(form.getBillingAddress());
        account.setShippingAddress(form.getShippingAddress());
        account.setPhoneNumber(form.getPhoneNumber());
        this.accountService.saveAccount(account);
    }

    @RequestMapping(value = "account/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id)
    {
        if(this.accountService.getAccount(id) == null)
            throw new ResourceNotFoundException();
        this.accountService.deleteAccount(id);
    }

    @XmlRootElement(name = "accounts")
    public static class AccountList
    {
        private List<Account> value;

        @XmlElement(name = "account")
        public List<Account> getValue()
        {
            return value;
        }

        public void setValue(List<Account> accounts)
        {
            this.value = accounts;
        }
    }
}
