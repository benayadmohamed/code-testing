package com.models;

import java.math.BigDecimal;

public class Account {

    private String code;
    private BigDecimal Solde;

    public Account(String code, BigDecimal solde) {
        this.code = code;
        Solde = solde;
    }

    public Account(String code) {
        this.code = code;
    }

    public Account() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getSolde() {
        return Solde;
    }

    public void setSolde(BigDecimal solde) {
        Solde = solde;
    }
}
