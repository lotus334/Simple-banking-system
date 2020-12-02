package org.hyperskill.banking.user_actions;


import org.hyperskill.banking.*;
import org.hyperskill.banking.input_output.Input;
import org.hyperskill.banking.input_output.Output;

public class CreateAction implements UserAction {
    private final Output out;

    public CreateAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Create an account";
    }

    @Override
    public boolean execute(Input input, Storage storage) {
        Account account = new Account();
        storage.add(account);
        out.println("Your card has been created\n" +
                "Your card number:\n" +
                account.getNumber() + "\n" +
                "Your card PIN:\n" +
                account.getPin() + "\n");
        return true;
    }
}
