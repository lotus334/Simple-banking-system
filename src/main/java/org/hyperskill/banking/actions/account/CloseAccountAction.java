package org.hyperskill.banking.actions.account;

import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.io.Input;
import org.hyperskill.banking.io.Output;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class CloseAccountAction<T> implements Action<T> {
    private final Output out;
    private final DataSource dataSource;

    public CloseAccountAction(Output out, DataSource dataSource) {
        this.out = out;
        this.dataSource = dataSource;
    }

    @Override
    public String name() {
        return "Close account";
    }

    @Override
    public boolean execute(Input input, T t) {
        var map = (Map<String, String>) t;
        try (Connection con = dataSource.getConnection()){
            String insert = "DELETE FROM card WHERE id = ?;";

            try (PreparedStatement preparedStatement = con.prepareStatement(insert)){
                preparedStatement.setInt(1, Integer.parseInt(map.get("id")));

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("The account has been closed!");
        return false;
    }
}
