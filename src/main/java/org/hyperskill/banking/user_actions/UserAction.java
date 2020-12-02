package org.hyperskill.banking.user_actions;

import org.hyperskill.banking.input_output.Input;
import org.hyperskill.banking.Storage;

public interface UserAction {
    String name();

    boolean execute(Input input, Storage storage);
}
