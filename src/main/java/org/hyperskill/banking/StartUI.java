package org.hyperskill.banking;

import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.io.Input;
import org.hyperskill.banking.io.Output;

import java.util.List;

public class StartUI<T> {
    private final Output out;

    public StartUI(Output out) {
        this.out = out;
    }

    public boolean init(Input input, T t, List<Action> actions) {
        boolean runMain = true;
        boolean run = true;
        int select;
        while (run) {
            showMenu(actions);
            select = input.askInt("");
            if (select < 0 || select >= actions.size()) {
                out.println("Wrong input, you can select: 0..." + (actions.size() - 1));
                continue;
            }
            Action action;
            if (select == 0) {
                action = actions.get(actions.size() - 1);
                runMain = false;
            } else {
                action = actions.get(select - 1);
            }
            run = action.execute(input, t);
        }
        return runMain;
    }

    private void showMenu(List<Action> actions) {
        for (int index = 0; index < actions.size() - 1; index++) {
            out.println((index + 1) + ". " + actions.get(index).name());
        }
        out.println("0. " + actions.get(actions.size() - 1).name());
    }
}
