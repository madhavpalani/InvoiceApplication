package com.madhav.com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FetchProductDetails {

    public void fetchAllProductDetails() {
        String query = "SELECT productId, productName, productPrice, totalSoldAmount,availableProducts FROM ProductDetails";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                double productPrice = resultSet.getDouble("productPrice");
                double totalSoldAmount = resultSet.getDouble("totalSoldAmount");
                int availableProducts = resultSet.getInt("availableProducts");

                System.out.println();
                System.out.println("Product ID: " + productId);
                System.out.println("Product Name: " + productName);
                System.out.println("Product Price: " + productPrice);
                System.out.println("Total Sold Amount: " + totalSoldAmount);
                System.out.println("Available Products: "+ availableProducts);
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
