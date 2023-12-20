package com.glady.challenge.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CompanyDTO {
    @Schema(name = "Company name", example = "Glady", required = true)
    private String name;
    @Schema(name = "Company amount", example = "1000", required = true)
    private int amount;

    public CompanyDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
