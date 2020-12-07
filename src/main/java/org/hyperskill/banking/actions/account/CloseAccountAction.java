package org.hyperskill.banking.actions.account;

import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.io.Input;

public class CloseAccountAction implements Action {
    @Override
    public String name() {
        return "Close account";
    }

    @Override
    public boolean execute(Input input, Object o) {
        return false;
    }
}
