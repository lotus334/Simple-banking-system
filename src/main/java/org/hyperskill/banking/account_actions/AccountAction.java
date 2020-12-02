package org.hyperskill.banking.account_actions;

import org.hyperskill.banking.Account;
import org.hyperskill.banking.input_output.Input;

public interface AccountAction {
    String name();

    boolean execute(Input input, Account account);
}
