package org.hyperskill.banking.account_actions;

import org.hyperskill.banking.Account;
import org.hyperskill.banking.Storage;
import org.hyperskill.banking.input_output.Input;
import org.hyperskill.banking.input_output.Output;

public class BalanceAction implements AccountAction {
    private final Output out;

    public BalanceAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Balance";
    }

    @Override
    public boolean execute(Input input, Account account) {
        out.println("Balance: " + account.getBalance());
        return true;
    }
}
