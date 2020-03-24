package com.integrations;

import com.models.Account;
import com.models.VirementRequest;
import com.services.AccountService;
import com.services.VirementService;
import com.validators.VirementValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({VirementService.class, AccountService.class, VirementValidator.class})
public class VirementServiceTest {

    @Autowired
    private VirementService virementService;
    @Autowired
    private AccountService accountService;

    private Set<Account> accountsToDeleteAfterTest = new HashSet<>();

    @AfterEach
    void apresChaqueTest() {

        accountsToDeleteAfterTest.forEach(account ->
                accountService.deleteAccount(account.getCode()));
    }

    @Test
    public void testPerformTransferWithSuccess() {

        accountService.addAccount(new Account("c11", new BigDecimal(10)));
        accountService.addAccount(new Account("c12", new BigDecimal(20)));
        Account accountC11 = accountService.getAccount("c11");
        Account accountC12 = accountService.getAccount("c12");

        BigDecimal amountToTransform = new BigDecimal(10);
        VirementRequest virementRequest = new VirementRequest(amountToTransform, accountC11, accountC12);
        assertTrue(virementService.performTransfer(virementRequest));
        assertTrue(accountService.getAccount("c11").getSolde().equals(new BigDecimal(0)));
        assertTrue(accountService.getAccount("c12").getSolde().equals(new BigDecimal(30)));
        accountsToDeleteAfterTest.add(accountC11);
        accountsToDeleteAfterTest.add(accountC12);

    }

    @Test
    public void testPerformTransferWithFailureWhenSoleInsufficient() {

        accountService.addAccount(new Account("c13", new BigDecimal(9)));
        accountService.addAccount(new Account("c14", new BigDecimal(20)));

        Account accountC11 = accountService.getAccount("c13");
        Account accountC12 = accountService.getAccount("c14");


        BigDecimal amountToTransform = new BigDecimal(10);
        VirementRequest virementRequest = new VirementRequest(amountToTransform, accountC11, accountC12);
        assertFalse(virementService.performTransfer(virementRequest));

//        Exception exception = Assertions.assertThrows(
//                SoldeInfAZeroException.class,
//                () -> virementService.performTransfer(virementRequest));
        accountsToDeleteAfterTest.add(accountC11);
        accountsToDeleteAfterTest.add(accountC12);

    }

    @Test
    public void testPerformTransferWithFailureWhenAccountNotFound() {

        Account accountC11 = new Account("c66");
        Account accountC12 = new Account("c77");
        BigDecimal amountToTransform = new BigDecimal(10);
        VirementRequest virementRequest = new VirementRequest(amountToTransform, accountC11, accountC12);
        assertFalse(virementService.performTransfer(virementRequest));

    }

    @Test
    public void testPerformTransferWithFailureWhenMontantInfAZero() {

        accountService.addAccount(new Account("c15", new BigDecimal(9)));
        accountService.addAccount(new Account("c16", new BigDecimal(20)));
        Account accountC11 = accountService.getAccount("c15");
        Account accountC12 = accountService.getAccount("c16");
        BigDecimal amountToTransform = new BigDecimal(-1);
        VirementRequest virementRequest = new VirementRequest(amountToTransform, accountC11, accountC12);
        assertFalse(virementService.performTransfer(virementRequest));
        accountsToDeleteAfterTest.add(accountC11);
        accountsToDeleteAfterTest.add(accountC12);
    }
}
