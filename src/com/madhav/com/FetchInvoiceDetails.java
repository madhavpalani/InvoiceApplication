package com.madhav.com;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FetchInvoiceDetails {

    public void fetchInvoicesByDate(LocalDate desiredDate) {
        String query = "SELECT id.invoiceId, id.customerId, id.invoiceDate, id.totalAmount, id.isPaid, " +
                "ip.quantity, ip.unitPrice, pd.productName " +
                "FROM InvoiceDetails id " +
                "JOIN InvoiceProducts ip ON id.invoiceId = ip.invoiceId " +
                "JOIN ProductDetails pd ON ip.productId = pd.productId " +
                "WHERE id.invoiceDate = ?";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setObject(1, desiredDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            int lastInvoiceId = -1;
            StringBuilder productDetails = new StringBuilder();

            while (resultSet.next()) {
                int invoiceId = resultSet.getInt("invoiceId");
                int customerId = resultSet.getInt("customerId");
                LocalDate invoiceDate = resultSet.getObject("invoiceDate", LocalDate.class);
                double totalAmount = resultSet.getDouble("totalAmount");
                boolean isPaid = resultSet.getBoolean("isPaid");
                int quantity = resultSet.getInt("quantity");
                double unitPrice = resultSet.getDouble("unitPrice");
                String productName = resultSet.getString("productName");

                if (invoiceId != lastInvoiceId) {
                    if (lastInvoiceId != -1) {
                        System.out.println("Products: ");
                        System.out.println(productDetails.toString());
                        System.out.println();
                    }

                    System.out.println("Invoice ID: " + invoiceId);
                    System.out.println("Customer ID: " + customerId);
                    System.out.println("Invoice Date: " + invoiceDate);
                    System.out.println("Total Amount: " + totalAmount);
                    System.out.println("Paid Status: " + (isPaid ? "Paid" : "Not Paid"));

                    productDetails.setLength(0);
                    lastInvoiceId = invoiceId;
                }

                productDetails.append("  Product Name: ").append(productName)
                        .append(", Quantity: ").append(quantity)
                        .append(", Unit Price: ").append(unitPrice)
                        .append("\n");
            }

            if (lastInvoiceId != -1) {
                System.out.println("Products: ");
                System.out.println(productDetails.toString());
                System.out.println();
            }
            System.out.println("------------------------");


        } catch (SQLException e) {
            System.out.println("Excep0");
        }
    }
    public void fetchPaidInvoices() {
        String query = "SELECT id.invoiceId, id.customerId, id.invoiceDate, id.totalAmount, id.isPaid, " +
                "ip.quantity, ip.unitPrice, pd.productName, cd.customerName, cd.customerAddress, cd.customerEmail, cd.phoneNumber " +
                "FROM InvoiceDetails id " +
                "JOIN InvoiceProducts ip ON id.invoiceId = ip.invoiceId " +
                "JOIN ProductDetails pd ON ip.productId = pd.productId " +
                "JOIN CustomerDetails cd ON id.customerId = cd.customerId " +
                "WHERE id.isPaid = TRUE";

        try (
                Connection connection = connectDB.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            int lastInvoiceId = -1;
            StringBuilder productDetails = new StringBuilder();

            while (resultSet.next()) {
                int invoiceId = resultSet.getInt("invoiceId");
                int customerId = resultSet.getInt("customerId");
                LocalDate invoiceDate = resultSet.getObject("invoiceDate", LocalDate.class);
                double totalAmount = resultSet.getDouble("totalAmount");
                boolean isPaid = resultSet.getBoolean("isPaid");
                int quantity = resultSet.getInt("quantity");
                double unitPrice = resultSet.getDouble("unitPrice");
                String productName = resultSet.getString("productName");
                String customerName = resultSet.getString("customerName");
                String customerAddress = resultSet.getString("customerAddress");
                String customerEmail = resultSet.getString("customerEmail");
                String phoneNumber = resultSet.getString("phoneNumber");

                if (invoiceId != lastInvoiceId) {
                    if (lastInvoiceId != -1) {
                        System.out.println("Products: ");
                        System.out.println(productDetails.toString());
                        System.out.println();
                    }

                    System.out.println("Invoice ID: " + invoiceId);
                    System.out.println("Customer ID: " + customerId);
                    System.out.println("Customer Name: " + customerName);
                    System.out.println("Customer Address: " + customerAddress);
                    System.out.println("Customer Email: " + customerEmail);
                    System.out.println("Customer Phone Number: " + phoneNumber);
                    System.out.println("Invoice Date: " + invoiceDate);
                    System.out.println("Total Amount: " + totalAmount);
                    System.out.println("Paid Status: " + (isPaid ? "Paid" : "Not Paid"));

                    productDetails.setLength(0);
                    lastInvoiceId = invoiceId;
                }

                productDetails.append("  Product Name: ").append(productName)
                        .append(", Quantity: ").append(quantity)
                        .append(", Unit Price: ").append(unitPrice)
                        .append("\n");
            }

            if (lastInvoiceId != -1) {
                System.out.println("Products: ");
                System.out.println(productDetails.toString());
                System.out.println();
            }
            System.out.println("------------------------");

        } catch (SQLException e) {
            System.out.println(("Excep 1"));
        }
    }

}
