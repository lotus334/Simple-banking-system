package org.hyperskill.banking;

import org.hyperskill.banking.input_output.ConsoleInput;
import org.hyperskill.banking.input_output.ConsoleOutput;
import org.hyperskill.banking.input_output.Input;
import org.hyperskill.banking.input_output.Output;
import org.hyperskill.banking.user_actions.CreateAction;
import org.hyperskill.banking.user_actions.ExitAction;
import org.hyperskill.banking.user_actions.LogIntoAction;
import org.hyperskill.banking.user_actions.UserAction;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input input = new ConsoleInput();
        Storage storage = new Storage();
        List<UserAction> actions = Arrays.asList(
                new CreateAction(output),
                new LogIntoAction(output),
                new ExitAction()
        );
        new StartUI(output).init(input, storage, actions);
    }
}
