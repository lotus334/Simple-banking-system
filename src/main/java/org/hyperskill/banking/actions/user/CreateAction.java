package org.hyperskill.banking.actions.user;


import org.hyperskill.banking.*;
import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.io.Input;
import org.hyperskill.banking.io.Output;

public class CreateAction<T> implements Action<T> {
    private final Output out;

    public CreateAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Create an account";
    }

    @Override
    public boolean execute(Input input, T t) {
        Account account = new Account();
        Storage storage = (Storage) t;
        storage.add(account);
        out.println("Your card has been created\n"
                + "Your card number:\n"
                + account.getNumber() + "\n"
                + "Your card PIN:\n"
                + account.getPin() + "\n");
        return true;
    }
}
