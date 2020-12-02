package org.hyperskill.banking.actions.user;

import org.hyperskill.banking.*;
import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.actions.account.BalanceAction;
import org.hyperskill.banking.actions.ExitAction;
import org.hyperskill.banking.actions.account.LogOutAction;
import org.hyperskill.banking.io.Input;
import org.hyperskill.banking.io.Output;

import java.util.Arrays;
import java.util.List;

public class LogIntoAction<T> implements Action<T> {
    private final Output out;

    public LogIntoAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Log into account";
    }

    @Override
    public boolean execute(Input input, T t) {
        String number = input.askStr("Enter your card number:\n");
        String pin = input.askStr("Enter your PIN:\n");
        Storage storage = (Storage) t;
        var account = storage.logIntoAccount(number, pin);
        if (account.isPresent()) {
            out.println("You have successfully logged in!");
            List<Action> actions = Arrays.asList(
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
