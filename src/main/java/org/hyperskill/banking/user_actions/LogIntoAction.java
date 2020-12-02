package org.hyperskill.banking.user_actions;

import org.hyperskill.banking.*;
import org.hyperskill.banking.account_actions.AccountAction;
import org.hyperskill.banking.account_actions.BalanceAction;
import org.hyperskill.banking.account_actions.ExitAction;
import org.hyperskill.banking.account_actions.LogOutAction;
import org.hyperskill.banking.input_output.Input;
import org.hyperskill.banking.input_output.Output;

import java.util.Arrays;
import java.util.List;

public class LogIntoAction implements UserAction {
    private final Output out;

    public LogIntoAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Log into account";
    }

    @Override
    public boolean execute(Input input, Storage storage) {
        String number = input.askStr("Enter your card number:\n");
        String pin = input.askStr("Enter your PIN:\n");
        var account = storage.logIntoAccount(number, pin);
        if (account.isPresent()) {
            out.println("You have successfully logged in!");
            List<AccountAction> actions = Arrays.asList(
                    new BalanceAction(out),
                    new LogOutAction(out),
                    new ExitAction()
            );
            new AccountUI(out).init(input, account.get(), actions);

        } else {
            out.println("Wrong card number or PIN!");
        }
        return true;
    }
}
