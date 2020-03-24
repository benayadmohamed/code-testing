package com.unitTests;

import com.exceptions.AccountExistePasException;
import com.exceptions.MontantInfAZeroException;
import com.exceptions.SoldeInfAZeroException;
import com.models.Account;
import com.services.AccountService;
import com.validators.VirementValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class VirmenetValidatorTest {


    @Mock
    private AccountService accountService;

    @InjectMocks
    private VirementValidator virementValidator;

    @BeforeAll
    public static void init() {
        System.out.println("HHHHHHHHHHHHHHHhh");
    }

    @Test
    public void retriveMontantInfAZeroException_whenMontantIsInfAZero() {

        Exception exception = Assertions.assertThrows(
                MontantInfAZeroException.class,
                () -> virementValidator.validateAmount(new BigDecimal(-1)));

        assertTrue(exception.getMessage().contains("le Montant doit etre sup à 0"));

    }

    @Test
    public void retriveSoldeInfAZeroException_whenSoldeInfAZero() {

        Account emetteur = new Account("c1", new BigDecimal(999));

        Exception exception = Assertions.assertThrows(
                SoldeInfAZeroException.class,
                () -> virementValidator.validateSolde(emetteur, new BigDecimal(1000)));

        assertTrue(exception.getMessage().contains("le Solde n'est pas suffisant"));
    }

    @Test
    public void retriveAccountNotFoundException_whenAccountNotExisting() {
        BDDMockito.given(accountService.getAccount("c1")).willReturn(null);

        Exception exception = Assertions.assertThrows(
                AccountExistePasException.class,
                () -> virementValidator.validateAccount("c1"));

        assertTrue(exception.getMessage().contains("Account not found"));
    }

}
