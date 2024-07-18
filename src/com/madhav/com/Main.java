package com.madhav.com;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice=-1;

        do {
            try {
                System.out.println("Choose an option:");
                System.out.println("1. Fetch Invoice Details by Date");
                System.out.println("2. Fetch Customer Details by Phone Number");
                System.out.println("3. Fetch Paid Invoices");
                System.out.println("4. Fetch Product details");
                System.out.println("5. Add new Invoice");
                System.out.println("6. Add new Products");
                System.out.println("7. Add new Employee");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");

                if(scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    System.out.println("Invalid input. Please enter a number between 0 and 7.");
                    scanner.next();
                    continue;
                }

                switch (choice) {
                    case 1:
                        System.out.print("Enter desired date (YYYY-MM-DD): ");
                        String dateInput = scanner.next();
                        LocalDate desiredDate = LocalDate.parse(dateInput);
                        fetchInvoiceDetails(desiredDate);
                        break;
                    case 2:
                        System.out.print("Enter phone number: ");
                        String phoneNumber = scanner.next();
                        fetchCustomerDetails(phoneNumber);
                        break;
                    case 3:
                        fetchPaidInvoices();
                        break;
                    case 4:
                        fetchProductDetails();
                        break;
                    case 5:
                        addNewInvoice();
                        break;
                    case 6:
                        addNewProducts();
                        break;
                    case 7:
                        addNewEmployee();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose a number between 0 and 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 7.");
                scanner.next();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void fetchInvoiceDetails(LocalDate desiredDate) {
        FetchInvoiceDetails fetcher = new FetchInvoiceDetails();
        fetcher.fetchInvoicesByDate(desiredDate);
    }

    private static void fetchCustomerDetails(String phoneNumber) {
        FetchCustomerDetails fetcher = new FetchCustomerDetails();
        fetcher.fetchCustomerByPhoneNumber(phoneNumber);
    }

    private static void fetchPaidInvoices() {
        FetchInvoiceDetails fetcher = new FetchInvoiceDetails();
        fetcher.fetchPaidInvoices();
    }

    private static void fetchProductDetails() {
        FetchProductDetails fetcher = new FetchProductDetails();
        fetcher.fetchAllProductDetails();
    }

    private static void addNewInvoice() {
        addNewInvoice addNewInvoice = new addNewInvoice();
        addNewInvoice.addNewInvoice();
    }

    private static void addNewProducts(){
        AddNewProducts AddNewProducts = new AddNewProducts();
        AddNewProducts.addNewProduct();
    }

    private static void addNewEmployee(){
        AddNewEmployee addNewEmployee = new AddNewEmployee();
        addNewEmployee.addNewEmployee();
    }
}
