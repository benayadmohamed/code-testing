package com.services;

import com.exceptions.AccountExistePasException;
import com.exceptions.MontantInfAZeroException;
import com.exceptions.SoldeInfAZeroException;
import com.models.VirementRequest;
import com.validators.VirementValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VirementService {

    @Autowired
    AccountService accountService;
    @Autowired
    VirementValidator virementValidator;

    public boolean performTransfer(VirementRequest virementRequest) {
        try {
            virementValidator.validateVirementRequest(virementRequest);
            accountService.debiter(virementRequest.getEmetteur().getCode(), virementRequest.getAmount());
            accountService.crediter(virementRequest.getRecepteur().getCode(), virementRequest.getAmount());
            return true;
        } catch (AccountExistePasException | SoldeInfAZeroException | MontantInfAZeroException e) {
            return false;
        }
    }
}
