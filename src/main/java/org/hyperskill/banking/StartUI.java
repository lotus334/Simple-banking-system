package org.hyperskill.banking;

import org.hyperskill.banking.input_output.Input;
import org.hyperskill.banking.input_output.Output;
import org.hyperskill.banking.user_actions.UserAction;

import java.util.List;

public class StartUI {
    private final Output out;

    public StartUI(Output out) {
        this.out = out;
    }

    public void init(Input input, Storage storage, List<UserAction> actions) {
        boolean run = true;
        int select;
        while (run) {
            showMenu(actions);
            select = input.askInt("");
            if (select < 0 || select >= actions.size()) {
                out.println("Wrong input, you can select: 0..." + (actions.size() - 1));
                continue;
            }
            UserAction action;
            if (select == 0) {
                action = actions.get(actions.size() - 1);
            } else {
                action = actions.get(select - 1);
            }
            run = action.execute(input, storage);
        }
    }

    private void showMenu(List<UserAction> actions) {
        for (int index = 0; index < actions.size() - 1; index++) {
            out.println((index + 1) + ". " + actions.get(index).name());
        }
        out.println("0. " + actions.get(actions.size() - 1).name());
    }
}
