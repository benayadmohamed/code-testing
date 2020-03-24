package com.unitTests;

import com.exceptions.MontantInfAZeroException;
import com.exceptions.SoldeInfAZeroException;
import com.models.Account;
import com.models.VirementRequest;
import com.services.AccountService;
import com.services.VirementService;
import com.validators.VirementValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class VirementServiceTest {

    @InjectMocks
    private VirementService virementService;
    @Mock
    private AccountService accountService;
    @Mock
    private VirementValidator virementValidator;


    @Test
    public void testPerformTransferWithSuccess() {

        BDDMockito.given(accountService.getAccount("c11")).willReturn(new Account("c11", new BigDecimal(10)));
        BDDMockito.given(accountService.getAccount("c12")).willReturn(new Account("c12", new BigDecimal(20)));
        Account accountC11 = accountService.getAccount("c11");
        Account accountC12 = accountService.getAccount("c12");

        BigDecimal amountToTransform = new BigDecimal(10);
        VirementRequest virementRequest = new VirementRequest(amountToTransform, accountC11, accountC12);
        assertTrue(virementService.performTransfer(virementRequest));

    }

    @Test
    public void testPerformTransferWithFailureWhenSoleInsufficient() {

        BDDMockito.given(accountService.getAccount("c13"))
                .willReturn(new Account("c13", new BigDecimal(9)));
        BDDMockito.given(accountService.getAccount("c14"))
                .willReturn(new Account("c14", new BigDecimal(20)));

        Account accountC11 = accountService.getAccount("c13");
        Account accountC12 = accountService.getAccount("c14");


        BigDecimal amountToTransform = new BigDecimal(10);
        VirementRequest virementRequest = new VirementRequest(amountToTransform, accountC11, accountC12);

        BDDMockito.given(virementValidator.validateVirementRequest(virementRequest))
                .willThrow(new SoldeInfAZeroException("solde insuffisant"));

        assertFalse(virementService.performTransfer(virementRequest));


    }


    @Test
    public void testPerformTransferWithFailureWhenMontantInfAZero() {

        BDDMockito.given(accountService.getAccount("c15")).willReturn(new Account("c11", new BigDecimal(10)));
        BDDMockito.given(accountService.getAccount("c16")).willReturn(new Account("c12", new BigDecimal(20)));

        Account accountC11 = accountService.getAccount("c15");
        Account accountC12 = accountService.getAccount("c16");
        BigDecimal amountToTransform = new BigDecimal(-1);
        VirementRequest virementRequest = new VirementRequest(amountToTransform, accountC11, accountC12);
        BDDMockito.given(virementValidator.validateVirementRequest(virementRequest))
                .willThrow(new MontantInfAZeroException(""));

        assertFalse(virementService.performTransfer(virementRequest));

    }
}
