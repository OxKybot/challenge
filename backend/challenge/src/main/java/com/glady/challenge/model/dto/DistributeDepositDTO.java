package com.glady.challenge.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class DistributeDepositDTO {
    @Schema(name = "Company name", example = "Glady", required = true)
    private String CompanyName;
    @Schema(name = "user username", example = "Arashekian2", required = true)
    private String userName;
    @Schema(name = "Deposit price", example = "100", required = true)
    private int price;
    @Schema(name = "deposite type", example = "MEAL", required = true)
    private EnumDepositType depositType;

    public DistributeDepositDTO() {
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public EnumDepositType getDepositType() {
        return depositType;
    }

    public void setDepositType(EnumDepositType depositType) {
        this.depositType = depositType;
    }
}
