package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Account {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final List<Transaction> transactions = new ArrayList<>();
    private int balance = 0;


    public void deposit(int amount, String date) {
        validateAmount(amount);
        LocalDate parsedDate = parseDate(date);

        balance += amount;
        transactions.add(new Transaction(parsedDate, amount, balance));
    }

    public void withdraw(int amount, String date) {
        validateAmount(amount);
        LocalDate parsedDate = parseDate(date);

        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        balance -= amount;
        transactions.add(new Transaction(parsedDate, -amount, balance));
    }

    public void printStatement() {
        System.out.println("DATE | AMOUNT | BALANCE");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            System.out.println(
                    t.date.format(FORMATTER) + " | " +
                            t.amount + " | " +
                            t.balanceAfter
            );
        }
    }



    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format, expected dd-MM-yyyy");
        }
    }


    private static class Transaction {
        private final LocalDate date;
        private final int amount;
        private final int balanceAfter;

        private Transaction(LocalDate date, int amount, int balanceAfter) {
            this.date = date;
            this.amount = amount;
            this.balanceAfter = balanceAfter;
        }
    }
}
