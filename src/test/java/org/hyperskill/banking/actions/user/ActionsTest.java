package org.hyperskill.banking.actions.user;

import org.hyperskill.banking.StartUI;
import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.actions.ExitAction;
import org.hyperskill.banking.actions.account.AddIncomeAction;
import org.hyperskill.banking.io.*;
import org.junit.Test;
import org.junit.Before;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ActionsTest {
    private SQLiteDataSource dataSource = new SQLiteDataSource();

    @Before
    public void setUp() {
        String url = "jdbc:sqlite:testdb.db";
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

            String insert = "INSERT OR IGNORE INTO card(number, pin, balance) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = con.prepareStatement(insert)){
                preparedStatement.setString(1, "4000007084312555");
                preparedStatement.setString(2, "3357");
                preparedStatement.setNull(3, Types.INTEGER);

                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "4000007084312666");
                preparedStatement.setString(2, "3367");
                preparedStatement.setNull(3, Types.INTEGER);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String update = "UPDATE card SET balance = 0 WHERE id = ? OR id = ?";

            try (PreparedStatement preparedStatement = con.prepareStatement(update)){
                preparedStatement.setInt(1, 1);
                preparedStatement.setInt(2, 2);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logIntoValid() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"4000007084312666",
                        "3367",
                        "0"}
        );

        new LogIntoAction(out).execute(in, dataSource);

        assertThat(out.toString().replaceAll("[\\s]", ""), is(
                String.format("You have successfully logged in!"
                                + "1. Balance"
                                + "2. Add income"
                                + "3. Log out"
                                + "0. Exit"
                ).replaceAll("[\\s]", "")
        ));
    }

    @Test
    public void logIntoInvalid() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"4000007084312666",
                        "3357",
                        "0"}
        );

        new LogIntoAction(out).execute(in, dataSource);

        assertThat(out.toString().replaceAll("[\\s]", ""), is(
                String.format("Wrong card number or PIN!"
                ).replaceAll("[\\s]", "")
        ));
    }

    @Test
    public void logOutValid() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"4000007084312666",
                        "3367",
                        "3"}
        );

        new LogIntoAction(out).execute(in, dataSource);

        assertThat(out.toString().replaceAll("[\\s]", ""), is(
                String.format("You have successfully logged in!"
                        + "1. Balance"
                        + "2. Add income"
                        + "3. Log out"
                        + "0. Exit"
                        + "You have successfully logged out!"
                ).replaceAll("[\\s]", "")
        ));
    }

    @Test
    public void addIncomeValid() {
        String idForCheck = "1";
        String incomeForCheck = "900";
        String balance = null;

        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {
                        incomeForCheck}
        );

        var map = new HashMap<>(Map.of("id", idForCheck,
                "balance", "0"));

        new AddIncomeAction(out, dataSource).execute(in, map);

        try (Connection con = dataSource.getConnection()){
            String insert = "SELECT * FROM card WHERE id = ?;";

            try (PreparedStatement preparedStatement = con.prepareStatement(insert)){
                preparedStatement.setString(1, idForCheck);

                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    balance = String.valueOf(resultSet.getInt("balance"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertThat(incomeForCheck, is(balance));
    }
}