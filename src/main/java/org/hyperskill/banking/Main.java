package org.hyperskill.banking;

import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.actions.ExitAction;
import org.hyperskill.banking.io.ConsoleInput;
import org.hyperskill.banking.io.ConsoleOutput;
import org.hyperskill.banking.io.Input;
import org.hyperskill.banking.io.Output;
import org.hyperskill.banking.actions.user.CreateAction;
import org.hyperskill.banking.actions.user.LogIntoAction;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input input = new ConsoleInput();
        String url = "jdbc:sqlite:" + args[1];

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()){
            try (Statement statement = con.createStatement()){
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Action> actions = Arrays.asList(
                new CreateAction(output),
                new LogIntoAction(output),
                new ExitAction()
        );

        new StartUI(output).init(input, dataSource, actions);
    }
}
