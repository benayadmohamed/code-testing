package com.unitTests;

import com.models.Account;
import com.services.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {


    //    @Autowired
    @InjectMocks
    private AccountService accountService;


    @Test
    public void retrieveNullWhenAccountNotExist() {
        assertNull(accountService.getAccount("c4"));
    }

    @Test
    public void retrieveAccountWhenCodeAccountExistInList() {

        accountService.addAccount(new Account("c1", new BigDecimal(100)));

        assertEquals("c1", accountService.getAccount("c1").getCode());
    }

    @Test
    public void retrieveFalse_whenAccountNotDeleted() {
        assertFalse(accountService.deleteAccount("c100"));
    }

    @Test
    public void retrieveTrue_whenAccountDeleted() {
        accountService.addAccount(new Account("c200"));
        assertTrue(accountService.deleteAccount("c200"));
    }

    @Test
    public void debiterWithSuccessWhenBalanceIsSufficient() {


        accountService.addAccount(new Account("c10", new BigDecimal(100)));

        accountService.debiter("c10", new BigDecimal(40));

        assertTrue(accountService.getAccount("c10").getSolde().equals(new BigDecimal(60)));
    }

    @Test
    public void crediterWithSuccessWhenBalanceIncreased() {


        accountService.addAccount(new Account("c10", new BigDecimal(100)));

        accountService.crediter("c10", new BigDecimal(10));

        assertTrue(accountService.getAccount("c10").getSolde().equals(new BigDecimal(110)));
    }

}
