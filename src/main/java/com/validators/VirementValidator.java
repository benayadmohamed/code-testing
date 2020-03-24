package com.validators;

import com.exceptions.AccountExistePasException;
import com.exceptions.MontantInfAZeroException;
import com.exceptions.SoldeInfAZeroException;
import com.models.Account;
import com.models.VirementRequest;
import com.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class VirementValidator {

    @Autowired
    AccountService accountService;

    public boolean validateVirementRequest(VirementRequest virementRequest) throws AccountExistePasException, SoldeInfAZeroException, MontantInfAZeroException {
        validateAmount(virementRequest.getAmount());
        Account emitteur = validateAccount(virementRequest.getEmetteur().getCode());
        Account recepteur = validateAccount(virementRequest.getRecepteur().getCode());
        validateSolde(emitteur, virementRequest.getAmount());
        return true;
    }

    public Account validateAccount(String code) throws AccountExistePasException {
        Account account = accountService.getAccount(code);
        if (account == null)
            throw new AccountExistePasException("Account not found");
        return account;
    }

    public void validateAmount(BigDecimal amount) throws MontantInfAZeroException {
        if (amount.compareTo(new BigDecimal(0)) < 0) {
            throw new MontantInfAZeroException("le Montant doit etre sup à 0");
        }
    }

    public void validateSolde(Account emitteur, BigDecimal amount) throws SoldeInfAZeroException {
        if (emitteur.getSolde().compareTo(amount) < 0) {
            throw new SoldeInfAZeroException("le Solde n'est pas suffisant");
        }
    }
}
