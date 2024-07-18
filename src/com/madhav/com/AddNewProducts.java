package com.madhav.com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AddNewProducts {

    public void addNewProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your employee name: ");
        String employeeName = scanner.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        if (!authenticateEmployee(employeeName, password)) {
            System.out.println("Authentication failed or no access. Exiting.");
            return;
        }

        System.out.print("Enter product name: ");
        String productName = scanner.nextLine().trim();

        System.out.print("Enter product price: ");
        double productPrice = scanner.nextDouble();

        System.out.print("Enter available quantity: ");
        int availableProducts = scanner.nextInt();

        addProductToDatabase(productName, productPrice, availableProducts);

        System.out.println("Product added successfully.");
    }

    private boolean authenticateEmployee(String employeeName, String password) {
        String query = "SELECT employeerole, password FROM EmployeeDetails WHERE employeeName = ?";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, employeeName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                String role = resultSet.getString("employeerole");

                if (storedPassword.equals(encryptPassword(password)) && "manager".equalsIgnoreCase(role)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void addProductToDatabase(String productName, double productPrice, int availableProducts) {
        String insertProductQuery = "INSERT INTO ProductDetails (productName, productPrice, availableProducts, totalSoldAmount) " +
                "VALUES (?, ?, ?, 0.0)";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertProductQuery);
        ) {
            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, productPrice);
            preparedStatement.setInt(3, availableProducts);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
