package org.hyperskill.banking;

import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.actions.ExitAction;
import org.hyperskill.banking.io.ConsoleInput;
import org.hyperskill.banking.io.ConsoleOutput;
import org.hyperskill.banking.io.Input;
import org.hyperskill.banking.io.Output;
import org.hyperskill.banking.actions.user.CreateAction;
import org.hyperskill.banking.actions.user.LogIntoAction;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input input = new ConsoleInput();
        Storage storage = new Storage();
        List<Action> actions = Arrays.asList(
                new CreateAction(output),
                new LogIntoAction(output),
                new ExitAction()
        );
        new StartUI(output).init(input, storage, actions);
    }
}
