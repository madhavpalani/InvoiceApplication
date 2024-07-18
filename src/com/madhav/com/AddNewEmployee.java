package com.madhav.com;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddNewEmployee {

    public void addNewEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter employee name: ");
        String employeeName = scanner.nextLine().trim();

        System.out.print("Enter employee email: ");
        String employeeEmail = scanner.nextLine().trim();

        System.out.print("Enter employee role: ");
        String employeeRole = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        String encryptedPassword = encryptPassword(password);

        String insertEmployeeQuery = "INSERT INTO EmployeeDetails (employeeName, employeeemail, employeerole, password) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertEmployeeQuery);
        ) {
            preparedStatement.setString(1, employeeName);
            preparedStatement.setString(2, employeeEmail);
            preparedStatement.setString(3, employeeRole);
            preparedStatement.setString(4, encryptedPassword);

            preparedStatement.executeUpdate();

            System.out.println("New employee added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
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
}
