package com.madhav.com;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class addNewInvoice {

    public void addNewInvoice() {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Check if customer is new or existing
        System.out.print("Is the customer new? (yes/no): ");
        String isNewCustomer = scanner.nextLine().trim().toLowerCase();

        int customerId = -1;

        if ("yes".equals(isNewCustomer)) {
            // Step 2: If new, collect customer details and store
            customerId = addNewCustomer(scanner);
            if (customerId == -1) {
                System.out.println("Failed to add new customer. Exiting.");
                return;
            }
        } else if ("no".equals(isNewCustomer)) {
            // Step 3: If existing, match with phone number
            System.out.print("Enter customer phone number: ");
            String phoneNumber = scanner.nextLine().trim();

            customerId = findCustomerByPhoneNumber(phoneNumber);
            if (customerId == -1) {
                System.out.println("Customer not found.");
                return;
            }
        } else {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            return;
        }

        // Step 4: Create a new invoice in InvoiceDetails table with totalAmount 0
        int invoiceId = createNewInvoice(customerId);

        if (invoiceId == -1) {
            System.out.println("Failed to create new invoice. Exiting.");
            return;
        }

        // Step 5: Display available products
        displayAvailableProducts();

        // Step 6: Add products to invoice and calculate total amount
        double totalAmount = addProductsToInvoice(invoiceId, scanner);

        // Step 7: Update totalAmount in InvoiceDetails table
        updateInvoiceTotalAmount(invoiceId, totalAmount);

        // Step 8: Get isPaid status
        System.out.print("Is the invoice paid? (yes/no): ");
        boolean isPaid = scanner.next().trim().equalsIgnoreCase("yes");

        // Step 9: Update customer's totalDue if invoice is not paid
        if (!isPaid) {
            updateCustomerTotalDue(customerId, totalAmount);
        }
        else{
            updateInvoicePaidStatus(invoiceId);
        }

        scanner.close();
    }

    private int addNewCustomer(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine().trim();

        System.out.print("Enter customer address: ");
        String customerAddress = scanner.nextLine().trim();

        System.out.print("Enter customer email: ");
        String customerEmail = scanner.nextLine().trim();

        System.out.print("Enter customer phone number: ");
        String phoneNumber = scanner.nextLine().trim();

        int existingCustomerId = findCustomerByPhoneNumber(phoneNumber);
        if (existingCustomerId != -1) {
            System.out.println("Customer with this phone number already exists. Use existing customer.");
            return existingCustomerId;
        }

        String insertCustomerQuery = "INSERT INTO CustomerDetails (customerName, customerAddress, customerEmail, phoneNumber, totalDue) " +
                "VALUES (?, ?, ?, ?, 0.0) RETURNING customerId";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertCustomerQuery);
        ) {
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, customerAddress);
            preparedStatement.setString(3, customerEmail);
            preparedStatement.setString(4, phoneNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1); // Return the newly generated customerId
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private int findCustomerByPhoneNumber(String phoneNumber) {
        String query = "SELECT customerId FROM CustomerDetails WHERE phoneNumber = ?";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, phoneNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("customerId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private int createNewInvoice(int customerId) {
        String insertInvoiceQuery = "INSERT INTO InvoiceDetails (customerId, invoiceDate, totalAmount, isPaid) " +
                "VALUES (?, ?, 0.0, false) RETURNING invoiceId";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertInvoiceQuery);
        ) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setObject(2, LocalDate.now());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void displayAvailableProducts() {
        String query = "SELECT productId, productName, availableProducts FROM ProductDetails";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            System.out.println("Available Products:");
            while (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                int availableProducts = resultSet.getInt("availableProducts");

                System.out.println("Product ID: " + productId + ", Product Name: " + productName +
                        ", Available Products: " + availableProducts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double addProductsToInvoice(int invoiceId, Scanner scanner) {
        double totalAmount = 0.0;

        while (true) {
            System.out.print("Enter product ID to add (or 0 to finish): ");
            int productId = scanner.nextInt();
            if (productId == 0) {
                break;
            }

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();

            double unitPrice = -1;
            int availableProducts = -1;

            String query = "SELECT productPrice, availableProducts FROM ProductDetails WHERE productId = ?";

            try (
                    Connection connection = connectDB.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
            ) {
                preparedStatement.setInt(1, productId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    unitPrice = resultSet.getDouble("productPrice");
                    availableProducts = resultSet.getInt("availableProducts");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (unitPrice == -1 || availableProducts == -1) {
                System.out.println("Product not found or details not available. Please try again.");
                continue;
            }

            if (quantity > availableProducts) {
                System.out.println("Requested quantity exceeds available inventory. Available: " + availableProducts);
                continue;
            }

            String insertInvoiceProductQuery = "INSERT INTO InvoiceProducts (invoiceId, productId, quantity, unitPrice) " +
                    "VALUES (?, ?, ?, ?)";

            try (
                    Connection connection = connectDB.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(insertInvoiceProductQuery);
            ) {
                preparedStatement.setInt(1, invoiceId);
                preparedStatement.setInt(2, productId);
                preparedStatement.setInt(3, quantity);
                preparedStatement.setDouble(4, unitPrice);

                preparedStatement.executeUpdate();

                totalAmount += quantity * unitPrice;

                updateProductAvailableProducts(productId, availableProducts - quantity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalAmount;
    }

    private void updateProductAvailableProducts(int productId, int remainingProducts) {
        String updateAvailableProductsQuery = "UPDATE ProductDetails SET availableProducts = ? WHERE productId = ?";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateAvailableProductsQuery);
        ) {
            preparedStatement.setInt(1, remainingProducts);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateInvoiceTotalAmount(int invoiceId, double totalAmount) {
        String updateQuery = "UPDATE InvoiceDetails SET totalAmount = ? WHERE invoiceId = ?";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        ) {
            preparedStatement.setDouble(1, totalAmount);
            preparedStatement.setInt(2, invoiceId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCustomerTotalDue(int customerId, double totalAmount) {
        String updateDueQuery = "UPDATE CustomerDetails SET totalDue = totalDue + ? WHERE customerId = ?";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateDueQuery);
        ) {
            preparedStatement.setDouble(1, totalAmount);
            preparedStatement.setInt(2, customerId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateInvoicePaidStatus(int invoiceId){
        String updateStatusQuery = "UPDATE InvoiceDetails SET ispaid= TRUE WHERE invoiceId = ?";
        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateStatusQuery);
        ) {
            preparedStatement.setInt(1, invoiceId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
