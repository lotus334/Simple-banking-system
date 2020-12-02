package org.hyperskill.banking;

import org.hyperskill.banking.account_actions.AccountAction;
import org.hyperskill.banking.input_output.Input;
import org.hyperskill.banking.input_output.Output;
import org.hyperskill.banking.input_output.StubInput;
import org.hyperskill.banking.input_output.StubOutput;
import org.hyperskill.banking.user_actions.CreateAction;
import org.hyperskill.banking.user_actions.ExitAction;
import org.hyperskill.banking.user_actions.LogIntoAction;
import org.hyperskill.banking.user_actions.UserAction;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StartUITest {
    @Test
    public void whenInvalidExit() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"2", "0"}
        );
        Storage storage = new Storage();
        List<UserAction> actions = Arrays.asList(
                new ExitAction()
        );
        new StartUI(out).init(in, storage, actions);
        assertThat(out.toString(), is(
                String.format(
                        "0. Exit%n"
                                + "Wrong input, you can select: 0...0%n"
                                + "0. Exit%n"
                )
        ));
    }

    @Test
    public void whenExit() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"0"}
        );
        Storage storage = new Storage();
        List<UserAction> actions = Arrays.asList(
                new ExitAction()
        );
        new StartUI(out).init(in, storage, actions);
        assertThat(out.toString(), is(
                "0. Exit" + System.lineSeparator()
        ));
    }

    @Test
    public void whenValidLogIntoAction() {
        Output out = new StubOutput();
        Storage storage = new Storage();
        Account account = new Account();
        storage.add(account);
        List<UserAction> actions = Arrays.asList(
                new LogIntoAction(out),
                new ExitAction()
        );
        Input in = new StubInput(
                new String[] {
                        "1",
                        account.getNumber(),
                        account.getPin(),
                        "0",
                        "0"}
        );
        new StartUI(out).init(in, storage, actions);
        assertThat(out.toString(), is(
                "1. Log into account" + System.lineSeparator()
                        + "0. Exit" + System.lineSeparator()
                        + "You have successfully logged in!" + System.lineSeparator()
                        + "1. Balance" + System.lineSeparator()
                        + "2. Log out" + System.lineSeparator()
                        + "0. Exit" + System.lineSeparator()
                        + "1. Log into account" + System.lineSeparator()
                        + "0. Exit" + System.lineSeparator()
        ));
    }

    @Test
    public void whenInvalidLogIntoAction() {
        Output out = new StubOutput();
        Storage storage = new Storage();
        Account account1 = new Account();
        storage.add(account1);
        List<UserAction> actions = Arrays.asList(
                new LogIntoAction(out),
                new ExitAction()
        );
        Input in = new StubInput(
                new String[] {
                        "1",
                        account1.getPin(),
                        account1.getPin(),
                        "0",
                        "0"}
        );
        new StartUI(out).init(in, storage, actions);
        assertThat(out.toString(), is(
                "1. Log into account" + System.lineSeparator()
                        + "0. Exit" + System.lineSeparator()
                        + "Wrong card number or PIN!" + System.lineSeparator()
                        + "1. Log into account" + System.lineSeparator()
                        + "0. Exit" + System.lineSeparator()
        ));
    }
}