package com.glady.challenge.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glady.challenge.ChallengeApplication;
import com.glady.challenge.model.dto.CompanyDTO;
import com.glady.challenge.model.dto.DistributeDepositDTO;
import com.glady.challenge.model.dto.UserDTO;
import com.glady.challenge.model.entities.Company;
import com.glady.challenge.model.entities.User;
import com.glady.challenge.repository.CompanyRepository;
import com.glady.challenge.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static com.glady.challenge.model.dto.EnumDepositType.MEAL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ChallengeApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class ChallengeControllerIT {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    public void should_create_company() throws Exception {
        //GIVEN: user want to create a company
        CompanyDTO companyDTO = createCompanyDTO("Google");
        //WHEN: user validate the creation
        mvc.perform(MockMvcRequestBuilders
                        .post("/company")
                        .content(asJsonString(companyDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //THEN: the company is correctly created
        Optional<Company> company = companyRepository.findById("Google");
        Assertions.assertTrue(company.isPresent());
    }

    @Test
    @Order(2)
    public void should_not_create_company_when_already_exist() throws Exception {
        //GIVEN: user want to create a company that already exist
        CompanyDTO companyDTO = createCompanyAndReturnDTO("Glady");
        //WHEN: user validate the creation
        mvc.perform(MockMvcRequestBuilders
                        .post("/company")
                        .content(asJsonString(companyDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //THEN: receive a bad request status
                .andExpect(status().isBadRequest());
    }

    private CompanyDTO createCompanyAndReturnDTO(String name) {
        CompanyDTO companyDTO = createCompanyDTO(name);
        companyRepository.save(new Company(companyDTO));
        return companyDTO;
    }

    private static CompanyDTO createCompanyDTO(String name) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setAmount(1000);
        companyDTO.setName(name);
        return companyDTO;
    }

    @Test
    @Order(3)
    public void should_create_user() throws Exception {
        //GIVEN: company want to create a user
        createCompanyAndReturnDTO("Apple");
        UserDTO userDTO = createUserDTO("toto", "Apple");
        //WHEN: company validate the creation
        mvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .content(asJsonString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //THEN: the user is correctly created
        Optional<User> user = userRepository.findById("toto");
        Assertions.assertTrue(user.isPresent());
    }

    private static UserDTO createUserDTO(String username, String companyName) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(username);
        userDTO.setCompanyName(companyName);
        return userDTO;
    }

    @Test
    @Order(4)
    public void should_not_create_user_homonym() throws Exception {
        //GIVEN: company want to create a user with an already existing username
        UserDTO userDTO = createCompanyAndUserAndReturnDTO("Zozo", "waz");
        //WHEN: user validate the creation
        mvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .content(asJsonString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //THEN: receive a bad request status
                .andExpect(status().isBadRequest());
    }

    private UserDTO createCompanyAndUserAndReturnDTO(String username, String companyName) {
        CompanyDTO companyDTO = createCompanyAndReturnDTO(companyName);
        UserDTO userDTO = createUserDTO(username, companyName);
        userRepository.save(new User(username, new Company(companyDTO)));
        return userDTO;
    }

    @Test
    @Order(5)
    public void should_distribute_deposit() throws Exception {
        //GIVEN: company want to distribute deposit to a user
        DistributeDepositDTO distributeDepositDTO = createDistributeDepositDTO(100, "zoula", "bigOne");
        //WHEN: company validate the distribution
        mvc.perform(MockMvcRequestBuilders
                        .post("/company/distribute")
                        .content(asJsonString(distributeDepositDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //THEN: the deposit is correctly created
                .andExpect(status().isCreated());

    }

    private DistributeDepositDTO createDistributeDepositDTO(int price, String username, String companyName) {
        createCompanyAndUserAndReturnDTO(username, companyName);
        DistributeDepositDTO distributeDepositDTO = new DistributeDepositDTO();
        distributeDepositDTO.setDepositType(MEAL);
        distributeDepositDTO.setPrice(price);
        distributeDepositDTO.setCompanyName(companyName);
        distributeDepositDTO.setUserName(username);
        return distributeDepositDTO;
    }

    @Test
    @Order(6)
    public void should_not_distribute_deposit_when_company_amount_exceeded() throws Exception {
        //GIVEN: company want to distribute deposit to a user with an exceeded price
        DistributeDepositDTO distributeDepositDTO = createDistributeDepositDTO(1100, "marceau", "vromOne");
        //WHEN: company validate the distribution
        mvc.perform(MockMvcRequestBuilders
                        .post("/company/distribute")
                        .content(asJsonString(distributeDepositDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //THEN: the user is not created
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    public void should_return_user_balance() throws Exception {
        //GIVEN: user have deposit
        DistributeDepositDTO distributeDepositDTO = createDistributeDepositDTO(100, "mac", "manyMan");
        mvc.perform(MockMvcRequestBuilders
                        .post("/company/distribute")
                        .content(asJsonString(distributeDepositDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //WHEN: user want to know his balance
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/user?userName=mac")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        //THEN: the result is correct
        Assertions.assertTrue(content.contains("100"));

    }
}
