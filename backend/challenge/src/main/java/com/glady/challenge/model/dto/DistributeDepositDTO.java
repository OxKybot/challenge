package com.glady.challenge.model.dto;

public class DistributeDepositDTO {
    private String CompanyName;
    private String userName;
    private int price;
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
