package com.glady.challenge.controller;

import com.glady.challenge.model.dto.CompanyDTO;
import com.glady.challenge.model.dto.DistributeDepositDTO;
import com.glady.challenge.model.dto.UserBalanceDTO;
import com.glady.challenge.model.dto.UserDTO;
import com.glady.challenge.service.CompanyService;
import com.glady.challenge.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChallengeController {
    private final UserService userService;
    private final CompanyService companyService;

    public ChallengeController(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserDTO userDTO) {
        companyService.createUser(userDTO);
    }

    @PostMapping("/company")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCompany(@RequestBody CompanyDTO companyDTO) {
        companyService.createCompany(companyDTO);
    }

    @PostMapping("/company/distribute")
    @ResponseStatus(HttpStatus.CREATED)
    public void distributeDeposit(@RequestBody DistributeDepositDTO distributeDepositDTO) {
        companyService.distributeDepositToUser(distributeDepositDTO);
    }

    @GetMapping("/user")
    public UserBalanceDTO getUserBalance(@RequestParam(value = "userName") String userName) {
        int balance = userService.getUserBalance(userName);
        return new UserBalanceDTO(userName, balance);
    }
}
