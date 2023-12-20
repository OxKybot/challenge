package com.glady.challenge.service;

import com.glady.challenge.helper.DepositHelper;
import com.glady.challenge.model.dto.DistributeDepositDTO;
import com.glady.challenge.model.dto.UserDTO;
import com.glady.challenge.model.entities.Company;
import com.glady.challenge.model.entities.Deposit;
import com.glady.challenge.model.entities.User;
import com.glady.challenge.repository.DepositRepository;
import com.glady.challenge.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DepositRepository depositRepository;

    public UserService(UserRepository userRepository, DepositRepository depositRepository) {
        this.userRepository = userRepository;
        this.depositRepository = depositRepository;
    }

    /**
     * find user by username
     *
     * @param userName
     * @return user
     */
    public User findUserByUserName(String userName) {
        Optional<User> user = userRepository.findById(userName);
        if (user.isPresent()) {
            return user.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
    }

    /**
     * add deposit to user account
     *
     * @param distributeDepositDTO
     */
    public void addDeposit(DistributeDepositDTO distributeDepositDTO) {
        User user = findUserByUserName(distributeDepositDTO.getUserName());
        Deposit deposit = new Deposit(distributeDepositDTO, user);
        depositRepository.save(deposit);
    }

    /**
     * create a new user
     *
     * @param userDTO
     * @param company
     */
    public void createUser(UserDTO userDTO, Company company) {
        if (canCreateUser(userDTO, company)) {
            User user = new User(userDTO.getUserName(), company);
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user already exist");
        }
    }

    /**
     * create a new user
     *
     * @param userDTO
     * @param company
     * @return
     */
    private boolean canCreateUser(UserDTO userDTO, Company company) {
        return userRepository.findByCompanyAndUserName(company, userDTO.getUserName()).isEmpty();
    }

    /**
     * get user balance
     *
     * @param userName
     * @return
     */
    public int getUserBalance(String userName) {
        User user = findUserByUserName(userName);
        return DepositHelper.calculateBalance(user.getDeposits());
    }
}
