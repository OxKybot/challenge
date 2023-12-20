package com.glady.challenge.service;

import com.glady.challenge.model.dto.CompanyDTO;
import com.glady.challenge.model.dto.DistributeDepositDTO;
import com.glady.challenge.model.dto.UserDTO;
import com.glady.challenge.model.entities.Company;
import com.glady.challenge.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserService userService;

    public CompanyService(CompanyRepository companyRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.userService = userService;
    }

    /**
     * create a new company
     *
     * @param companyDTO
     */
    public void createCompany(CompanyDTO companyDTO) {
        if (companyRepository.findById(companyDTO.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "company already exist");
        } else {
            companyRepository.save(new Company(companyDTO));
        }
    }

    /**
     * create a new user
     *
     * @param userDTO
     */
    public void createUser(UserDTO userDTO) {
        Company company = findCompanyByName(userDTO.getCompanyName());
        userService.createUser(userDTO, company);
    }

    /**
     * find company by name
     *
     * @param companyName
     * @return company
     */
    public Company findCompanyByName(String companyName) {
        Optional<Company> company = companyRepository.findById(companyName);
        if (company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "company not found");
        } else {
            return company.get();
        }
    }

    /**
     * distribute deposit to user
     *
     * @param distributeDepositDTO
     */
    public void distributeDepositToUser(DistributeDepositDTO distributeDepositDTO) {
        Company company = findCompanyByName(distributeDepositDTO.getCompanyName());
        //determine if the company has a sufficient balance
        if (company.getAmount() >= distributeDepositDTO.getPrice()) {
            userService.addDeposit(distributeDepositDTO);
            company.setAmount(company.getAmount() - distributeDepositDTO.getPrice());
            companyRepository.save(company);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "company amount exceeded");
        }
    }

}
