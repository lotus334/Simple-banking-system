package org.hyperskill.banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Account {
    private final String number;
    private final String pin;
    private int balance;

    public Account() {
        Random random = new Random();
        StringBuilder builderPin = new StringBuilder();
        StringBuilder builderNumber = new StringBuilder("400000");
        for (int i = 0; i < 4; i++) {
            builderPin.append(random.nextInt(10));
        }
        for (int i = 0; i < 10; i++) {
            builderNumber.append(random.nextInt(10));
        }
        pin = builderPin.toString();
        number = LuhnAlgorithm.createNumber(builderNumber.toString());
        balance = 0;
    }

    public String getNumber() {
        return number;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account{"
                + "number='" + number + '\''
                + ", pin='" + pin + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(getNumber(), account.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber());
    }
}
