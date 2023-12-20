package com.glady.challenge.unit.helper;

import com.glady.challenge.helper.DepositHelper;
import com.glady.challenge.model.entities.Deposit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static com.glady.challenge.model.dto.EnumDepositType.GIFTS;
import static com.glady.challenge.model.dto.EnumDepositType.MEAL;
import static org.junit.jupiter.api.Assertions.*;

class DepositHelperTest {

    @Test
    void should_return_false_when_gift_is_expired() {
        //GIVEN : user have a gift deposit
        Deposit deposit = new Deposit();
        deposit.setType(GIFTS);
        //WHEN : the gift is expired
        deposit.setReceivedDate(LocalDate.now().minusDays(600));
        //THEN : method return false
        assertFalse(DepositHelper.isValidDeposit(deposit));
    }

    @Test
    void should_return_true_when_gift_is_not_expired() {
        //GIVEN : user have a gift deposit
        Deposit deposit = new Deposit();
        deposit.setType(GIFTS);
        //WHEN : the gift is not expired
        deposit.setReceivedDate(LocalDate.now().minusDays(100));
        //THEN : method return true
        assertTrue(DepositHelper.isValidDeposit(deposit));
    }

    @Test
    void should_return_false_when_meal_is_expired() {
        //GIVEN : user have a meal deposit
        Deposit deposit = new Deposit();
        deposit.setType(MEAL);
        //WHEN : the meal is expired
        deposit.setReceivedDate(LocalDate.now().minusDays(600));
        //THEN : method return false
        assertFalse(DepositHelper.isValidDeposit(deposit));
    }

    @Test
    void should_return_true_when_meal_is_not_expired() {
        //GIVEN : user have a meal deposit
        Deposit deposit = new Deposit();
        deposit.setType(MEAL);
        //WHEN : the meal is not expired
        deposit.setReceivedDate(LocalDate.now().minusDays(100));
        //THEN : method return true
        assertTrue(DepositHelper.isValidDeposit(deposit));
    }

    @Test
    void should_calculate_balance() {
        //GIVEN : user have some deposit
        Deposit expiredDeposit = new Deposit();
        expiredDeposit.setType(MEAL);
        expiredDeposit.setReceivedDate(LocalDate.now().minusDays(100));
        expiredDeposit.setPrice(100);

        Deposit validDeposit = new Deposit();
        validDeposit.setType(MEAL);
        validDeposit.setReceivedDate(LocalDate.now().minusDays(600));
        validDeposit.setPrice(100);

        Set<Deposit> deposits = Set.of(expiredDeposit, validDeposit);

        //THEN : method return the correct value
        assertEquals(DepositHelper.calculateBalance(deposits), 100);
    }
}