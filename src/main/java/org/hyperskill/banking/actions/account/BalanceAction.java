package org.hyperskill.banking.actions.account;

import org.hyperskill.banking.Account;
import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.io.Input;
import org.hyperskill.banking.io.Output;

public class BalanceAction<T> implements Action<T> {
    private final Output out;

    public BalanceAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Balance";
    }

    @Override
    public boolean execute(Input input, T t) {
        Account acc = (Account) t;
        out.println("Balance: " + acc.getBalance());
        return true;
    }
}
