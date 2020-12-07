package org.hyperskill.banking.actions.user;

import org.hyperskill.banking.*;
import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.actions.account.AddIncomeAction;
import org.hyperskill.banking.actions.account.BalanceAction;
import org.hyperskill.banking.actions.ExitAction;
import org.hyperskill.banking.actions.account.CloseAccountAction;
import org.hyperskill.banking.actions.account.LogOutAction;
import org.hyperskill.banking.io.Input;
import org.hyperskill.banking.io.Output;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogIntoAction<T> implements Action<T> {
    private final Output out;

    public LogIntoAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Log into account";
    }

    @Override
    public boolean execute(Input input, T t) {
        String enteredNum = input.askStr("Enter your card number:\n");
        String enteredPin = input.askStr("Enter your PIN:\n");

        SQLiteDataSource dataSource = (SQLiteDataSource) t;

        String id = null;
        String number = null;
        String pin = null;
        String balance = null;

        try (Connection con = dataSource.getConnection()){
            String insert = "SELECT * FROM card WHERE number = ?;";

            try (PreparedStatement preparedStatement = con.prepareStatement(insert)){
                preparedStatement.setString(1, enteredNum);

                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    id = String.valueOf(resultSet.getInt("id"));
                    number = resultSet.getString("number");
                    pin = resultSet.getString("pin");
                    balance = String.valueOf(resultSet.getInt("balance"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>(Map.of(
                "id", id,
                "number", number,
                "pin", pin,
                "balance", balance));

        if (number != null && pin != null && pin.equals(enteredPin)) {
            out.println("You have successfully logged in!");
            List<Action> actions = Arrays.asList(
                    new BalanceAction(out),
                    new AddIncomeAction(out, dataSource),
                    new CloseAccountAction(out, dataSource),
                    new LogOutAction(out),
                    new ExitAction()
            );
            return new StartUI(out).init(input, map, actions);

        } else {
            out.println("Wrong card number or PIN!");
        }
        return true;
    }
}
