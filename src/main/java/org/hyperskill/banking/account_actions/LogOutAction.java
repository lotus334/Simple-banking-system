package org.hyperskill.banking.account_actions;

import org.hyperskill.banking.Account;
import org.hyperskill.banking.input_output.Input;
import org.hyperskill.banking.input_output.Output;

public class LogOutAction implements AccountAction{
    private final Output out;

    public LogOutAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Log out";
    }

    @Override
    public boolean execute(Input input, Account account) {
        out.println("You have successfully logged out!\n");
        return false;
    }
}
