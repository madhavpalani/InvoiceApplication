package com.madhav.com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FetchCustomerDetails {

    public void fetchCustomerByPhoneNumber(String phoneNumber) {
        String query = "SELECT customerId, customerName, customerAddress, customerEmail, totalDue " +
                "FROM CustomerDetails " +
                "WHERE phoneNumber = ?";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, phoneNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customerId");
                String customerName = resultSet.getString("customerName");
                String customerAddress = resultSet.getString("customerAddress");
                String customerEmail = resultSet.getString("customerEmail");
                double totalDue = resultSet.getDouble("totalDue");

                System.out.println("Customer ID: " + customerId);
                System.out.println("Customer Name: " + customerName);
                System.out.println("Customer Address: " + customerAddress);
                System.out.println("Customer Email: " + customerEmail);
                System.out.println("Total Due: " + totalDue);
                System.out.println();
                System.out.println("------------------------");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
