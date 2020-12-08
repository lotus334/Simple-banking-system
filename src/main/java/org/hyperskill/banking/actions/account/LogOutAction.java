package org.hyperskill.banking.actions.account;

import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.io.Input;
import org.hyperskill.banking.io.Output;

public class LogOutAction<T> implements Action<T> {
    private final Output out;

    public LogOutAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Log out";
    }

    @Override
    public boolean execute(Input input, T t) {
        out.println("You have successfully logged out!\n");
        return false;
    }
}
