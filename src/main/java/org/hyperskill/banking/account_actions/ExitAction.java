package org.hyperskill.banking.account_actions;

import org.hyperskill.banking.Account;
import org.hyperskill.banking.input_output.Input;

public class ExitAction implements AccountAction {
    @Override
    public String name() {
        return "Exit";
    }

    @Override
    public boolean execute(Input input, Account account) {
        return false;
    }
}
