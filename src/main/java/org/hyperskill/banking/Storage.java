package org.hyperskill.banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Storage {
    private List<Account> accountList = new ArrayList<>();

    public Account add(Account acc) {
        accountList.add(acc);
        return acc;
    }

    public Optional<Account> logIntoAccount(String number, String pin) {
        var account = findByNumber(number);
        if (account.isPresent()) {
            if (account.get().getPin().equals(pin)) {
                return account;
            }
        }
        return Optional.empty();
    }

    private Optional<Account> findByNumber(String number) {
        return accountList.stream()
                .filter(account -> account
                        .getNumber()
                        .equals(number))
                .findFirst();
    }
}
