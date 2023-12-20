package com.glady.challenge.helper;

import com.glady.challenge.model.entities.Deposit;

import java.time.LocalDate;

public class DepositHelper {
    public static boolean isValidDeposit(Deposit deposit) {
        return switch (deposit.getType()) {
            case GIFTS -> LocalDate.now().isBefore(deposit.getReceivedDate().plusDays(365));
            case MEAL -> isMealExpired(deposit.getReceivedDate(),LocalDate.now());
        };
    }

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
}
