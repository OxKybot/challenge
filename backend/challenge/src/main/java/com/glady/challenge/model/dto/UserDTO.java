package com.glady.challenge.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserDTO {
    @Schema(name = "user username", example = "arashkian2", required = true)
    private String userName;
    @Schema(name = "Company name", example = "Glady", required = true)
    private String companyName;

    public UserDTO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
