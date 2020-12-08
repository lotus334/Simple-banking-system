package org.hyperskill.banking.actions.account;

import org.hyperskill.banking.LuhnAlgorithm;
import org.hyperskill.banking.actions.Action;
import org.hyperskill.banking.io.Input;
import org.hyperskill.banking.io.Output;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.Map;

public class DoTransferAction<T> implements Action<T> {
    private final Output out;
    private final DataSource dataSource;

    public DoTransferAction(Output out, DataSource dataSource) {
        this.out = out;
        this.dataSource = dataSource;
    }

    @Override
    public String name() {
        return "Do transfer";
    }

    @Override
    public boolean execute(Input input, T t) {
        var map = (Map<String, String>) t;

        out.println("Transfer");
        String recipientNumber = input.askStr("Enter card number:\n");
        int recipientBalance = 0;

        if (isTheSameNumber(recipientNumber, map.get("number"))) {
            out.println("You can't transfer money to the same account!");
            return true;
        } else if (!isValidNumber(recipientNumber)) {
            out.println("Probably you made mistake in the card number. Please try again!");
            return true;
        }

        try (Connection con = dataSource.getConnection()){
            con.setAutoCommit(false);

            String insert = "SELECT * FROM card WHERE number = ?;";

            try (PreparedStatement selectStatement = con.prepareStatement(insert)){
                selectStatement.setString(1, recipientNumber);
                try (ResultSet resultSet = selectStatement.executeQuery()){
                    if (resultSet.next()) {
                        recipientBalance = resultSet.getInt("balance");
                    } else {
                        out.println("Such a card does not exist.");
                        return true;
                    }
                }
                int moneyToTransfer = input.askInt("Enter how much money you want to transfer:\n");
                if (moneyToTransfer > Integer.parseInt(map.get("balance"))) {
                    out.println("Not enough money!");
                    return true;
                }

                String decrease = "UPDATE card SET balance = ? WHERE number = ?";
                String increase = "UPDATE card SET balance = ? WHERE number = ?";

                PreparedStatement decreaseStatement = con.prepareStatement(decrease);
                decreaseStatement.setInt(1, Integer.parseInt(map.get("balance")) - moneyToTransfer);
                decreaseStatement.setString(2, map.get("number"));
                decreaseStatement.executeUpdate();

                PreparedStatement increaseStatement = con.prepareStatement(increase);
                increaseStatement.setInt(1, recipientBalance + moneyToTransfer);
                increaseStatement.setString(2, recipientNumber);
                increaseStatement.executeUpdate();

                map.put("balance", String.valueOf(Integer.parseInt(map.get("balance")) - moneyToTransfer));

                con.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("Success!");
        return true;
    }

    private boolean isTheSameNumber(String num1, String num2) {
        return num1.equals(num2);
    }

    private boolean isValidNumber(String number) {
        String validNum = LuhnAlgorithm.createNumber(number);
        return number.equals(validNum);
    }
}
