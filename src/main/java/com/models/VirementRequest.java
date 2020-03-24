package com.models;

import java.math.BigDecimal;

public class VirementRequest {
    private BigDecimal amount;
    private Account emetteur;
    private Account recepteur;

    public VirementRequest(BigDecimal amount, Account emetteur, Account recepteur) {
        this.amount = amount;
        this.emetteur = emetteur;
        this.recepteur = recepteur;
    }

    public VirementRequest() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(Account emetteur) {
        this.emetteur = emetteur;
    }

    public Account getRecepteur() {
        return recepteur;
    }

    public void setRecepteur(Account recepteur) {
        this.recepteur = recepteur;
    }
}
