package org.hyperskill.banking.actions;

import org.hyperskill.banking.io.Input;

public class ExitAction<T> implements Action<T> {
    @Override
    public String name() {
        return "Exit";
    }

    @Override
    public boolean execute(Input input, T t) {
        return false;
    }
}
