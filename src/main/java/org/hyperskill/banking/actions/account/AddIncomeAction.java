package org.hyperskill.banking.actions.account;

import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.io.Input;
import org.hyperskill.banking.io.Output;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;

public class AddIncomeAction<T> implements Action<T> {
    private final Output out;
    private final DataSource dataSource;

    public AddIncomeAction(Output out, DataSource dataSource) {
        this.out = out;
        this.dataSource = dataSource;
    }

    @Override
    public String name() {
        return "Add income";
    }

    @Override
    public boolean execute(Input input, T t) {
        int income = input.askInt("Enter income:\n");

        var map = (Map<String, String>) t;

        int newBalance = Integer.parseInt(map.get("balance")) + income;

        try (Connection con = dataSource.getConnection()){
            String insert = "UPDATE card SET balance = ? WHERE id = '" + map.get("id") + "';";

            try (PreparedStatement preparedStatement = con.prepareStatement(insert)){
                preparedStatement.setInt(1, newBalance);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("Income was added!\n");
        map.put("balance", String.valueOf(Integer.parseInt(map.get("balance")) + income));

        return true;
    }
}
