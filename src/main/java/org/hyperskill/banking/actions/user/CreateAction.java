package org.hyperskill.banking.actions.user;

import org.hyperskill.banking.*;
import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.io.Input;
import org.hyperskill.banking.io.Output;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class CreateAction<T> implements Action<T> {
    private final Output out;

    public CreateAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Create an account";
    }

    @Override
    public boolean execute(Input input, T t) {
        Account account = new Account();

        SQLiteDataSource dataSource = (SQLiteDataSource) t;

        try (Connection con = dataSource.getConnection()){
            String insert = "INSERT INTO card(number, pin, balance) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = con.prepareStatement(insert)){
                preparedStatement.setString(1, account.getNumber());
                preparedStatement.setString(2, account.getPin());
                preparedStatement.setNull(3, Types.INTEGER);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("Your card has been created\n"
                + "Your card number:\n"
                + account.getNumber() + "\n"
                + "Your card PIN:\n"
                + account.getPin() + "\n");
        return true;
    }
}
