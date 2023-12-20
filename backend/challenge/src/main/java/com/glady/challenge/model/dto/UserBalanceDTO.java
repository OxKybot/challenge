package com.glady.challenge.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserBalanceDTO {
    @Schema(name = "user username", example = "arashkian2", required = true)
    private String userName;
    @Schema(name = "user deposit balance", example = "200", required = true)
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
