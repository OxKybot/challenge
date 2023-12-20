package com.glady.challenge.model.dto;

public class UserBalanceDTO {
    private String userName;
    private int balance;

    public UserBalanceDTO() {
    }

    public UserBalanceDTO(String userName, int balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
