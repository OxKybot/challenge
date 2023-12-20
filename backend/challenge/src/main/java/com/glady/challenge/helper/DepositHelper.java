package com.glady.challenge.helper;

import com.glady.challenge.model.entities.Deposit;

import java.time.LocalDate;
import java.util.Set;

public class DepositHelper {
    /**
     * check if a deposit is expired
     *
     * @param deposit
     * @return boolean
     */
    public static boolean isValidDeposit(Deposit deposit) {
        //deposit validity depend on deposit type
        return switch (deposit.getType()) {
            case GIFTS -> LocalDate.now().isBefore(deposit.getReceivedDate().plusDays(365));
            case MEAL -> isMealExpired(deposit.getReceivedDate(), LocalDate.now());
        };
    }

    /**
     * check if a meal deposit is expired
     *
     * @param distributionDate
     * @param currentDate
     * @return boolean
     */
    private static boolean isMealExpired(LocalDate distributionDate, LocalDate currentDate) {
        // Calculate the end of February of the year following the distribution date
        LocalDate expiryDate = distributionDate.plusYears(1).withMonth(2).withDayOfMonth(28);

        // Adjust for leap year
        if (distributionDate.plusYears(1).isLeapYear()) {
            expiryDate = expiryDate.withDayOfMonth(29);
        }

        // Check if the current date is after the calculated expiry date
        return currentDate.isBefore(expiryDate);
    }

    public static int calculateBalance(Set<Deposit> deposits) {
        return deposits.stream()
                .filter(DepositHelper::isValidDeposit)
                .map(Deposit::getPrice)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
