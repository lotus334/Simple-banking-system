package org.hyperskill.banking.user_actions;

import org.hyperskill.banking.Storage;
import org.hyperskill.banking.input_output.Input;

public class ExitAction implements UserAction {
    @Override
    public String name() {
        return "Exit";
    }

    @Override
    public boolean execute(Input input, Storage storage) {
        return false;
    }
}
