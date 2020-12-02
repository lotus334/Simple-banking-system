package org.hyperskill.banking.actions;

import org.hyperskill.banking.io.Input;

public interface Action<T> {
    String name();

    boolean execute(Input input, T t);
}
