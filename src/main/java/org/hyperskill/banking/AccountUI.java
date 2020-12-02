package org.hyperskill.banking;

import org.hyperskill.banking.account_actions.AccountAction;
import org.hyperskill.banking.input_output.Input;
import org.hyperskill.banking.input_output.Output;
import org.hyperskill.banking.user_actions.UserAction;

import java.util.List;

public class AccountUI {
    private final Output out;

    public AccountUI(Output out) {
        this.out = out;
    }

    public void init(Input input, Account account, List<AccountAction> actions) {
        boolean run = true;
        int select;
        while (run) {
            showMenu(actions);
            select = input.askInt("");
            if (select < 0 || select >= actions.size()) {
                out.println("Wrong input, you can select: 0..." + (actions.size() - 1));
                continue;
            }
            AccountAction action;
            if (select == 0) {
                action = actions.get(actions.size() - 1);
            } else {
                action = actions.get(select - 1);
            }
            run = action.execute(input, account);
        }
    }

    private void showMenu(List<AccountAction> actions) {
        for (int index = 0; index < actions.size() - 1; index++) {
            out.println((index + 1) + ". " + actions.get(index).name());
        }
        out.println("0. " + actions.get(actions.size() - 1).name());
    }
}
