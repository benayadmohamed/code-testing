package com.services;

import com.models.Account;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {


    Map<String, Account> accounts = new HashMap<>();

    @PostConstruct
    private void init() {
        accounts.put("c1", new Account("c1", new BigDecimal(1000)));
        accounts.put("c2", new Account("c2", new BigDecimal(1000)));
        accounts.put("c3", new Account("c2", new BigDecimal(1000)));
    }


    public Account getAccount(String code) {
        return accounts.get(code);
    }

    public boolean addAccount(Account account) {
        if (accounts.containsKey(account.getCode()))
            return false;
        accounts.put(account.getCode(), account);
        return true;
    }

    public boolean deleteAccount(String code) {
        return accounts.remove(code) != null;
    }


    public void debiter(String code, BigDecimal amount) {
        Account account = accounts.get(code);
        account.setSolde(account.getSolde().subtract(amount));
    }

    public void crediter(String code, BigDecimal amount) {
        Account account = accounts.get(code);
        account.setSolde(account.getSolde().add(amount));
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }
}
