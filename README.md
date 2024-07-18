# Invoice Management Application

This Java application is designed to manage invoices, customer details, product details, and employee details in a business environment like grocery shop, supermarket, etc. It operates entirely in the terminal and interacts with a PostgreSQL database to store and retrieve information using JDBC (Java Database Connectivity).

## Features
- **Fetch Invoice Details by Date:** Retrieve and display invoice details for a specified date.
- **Fetch Customer Details by Phone Number:** Look up and display customer details using their phone number.
- **Fetch Paid Invoices:** List all invoices that have been marked as paid.
- **Fetch Product Details:** Retrieve and display details of products available in the inventory.
- **Add New Invoice:** Create a new invoice by specifying customer and product details, along with quantities and prices.
- **Add New Products:** Add new products to the inventory.
- **Add New Employee:** Add new employee details to the database, including employee name, email, and role.

## Database Schema

### InvoiceDetails
- **invoiceId**: SERIAL PRIMARY KEY
- **customerId**: INT NOT NULL
- **invoiceDate**: DATE NOT NULL
- **totalAmount**: DECIMAL(10, 2) NOT NULL
- **isPaid**: BOOLEAN DEFAULT FALSE
- **FOREIGN KEY** (customerId) REFERENCES CustomerDetails(customerId)
  
![image](https://github.com/user-attachments/assets/1eb60cf1-4c72-4486-9cc7-e4bc7e0ce793)


### ProductDetails
- **productId**: SERIAL PRIMARY KEY
- **productName**: VARCHAR(100) NOT NULL
- **productPrice**: DECIMAL(10, 2) NOT NULL
- **totalSoldAmount**: NUMERIC(10, 2)
- **availableProducts**: INT
  
![image](https://github.com/user-attachments/assets/12258a71-84f2-47c7-b3a3-1bb2da33eea2)

  
### CustomerDetails
- **customerId**: SERIAL PRIMARY KEY
- **customerName**: VARCHAR(100) NOT NULL
- **customerAddress**: VARCHAR(255)
- **customerEmail**: VARCHAR(100) UNIQUE
- **totalDue**: DECIMAL(10, 2)
- **phoneNumber**: VARCHAR(15) UNIQUE
  
![image](https://github.com/user-attachments/assets/e56d6e17-efa6-46db-bb8f-f35b633c45a2)

  
### InvoiceProducts
- **invoiceProductId**: SERIAL PRIMARY KEY
- **invoiceId**: INT NOT NULL
- **productId**: INT NOT NULL
- **quantity**: INT NOT NULL
- **unitPrice**: DECIMAL(10, 2) NOT NULL
- **FOREIGN KEY** (invoiceId) REFERENCES InvoiceDetails(invoiceId)
- **FOREIGN KEY** (productId) REFERENCES ProductDetails(productId)
  
![image](https://github.com/user-attachments/assets/34e10fa3-448b-4b63-b1cd-572e56bee612)

  
### EmployeeDetails
- **empId**: SERIAL PRIMARY KEY
- **empName**: VARCHAR(100) NOT NULL
- **empEmail**: VARCHAR(100) UNIQUE
- **role**: VARCHAR(50)
  
![image](https://github.com/user-attachments/assets/3bce5ebe-476e-43ff-899c-b9589e9731ca)


## Database Diagram
![PHOTO-2024-07-18-20-59-31](https://github.com/user-attachments/assets/b337a8a9-f9d8-4807-a676-197166f0e892)


## Error Handling
The application includes basic error handling to manage invalid user inputs and ensure data integrity.

## Prerequisites

- Java 8 or higher
- PostgreSQL 9.5 or higher

## Setup:

1. Clone the repository:

2. Set up the PostgreSQL database with the provided schema.

3. Update the database connection details in the application as needed.

4. Compile and run the application:

## Added Features Throughout the Development Process

- **Database Integration:**
  Implemented a robust database schema using PostgreSQL, facilitating efficient management of invoices, products, customers, and employee details.

- **Command-Line Interface (CLI):**
  Developed a user-friendly CLI application in Java for seamless interaction with the system, ensuring ease of use for administrators and users.

- **Invoice Management:**
  Enhanced invoice management capabilities with features for generating invoices, tracking payments, and retrieving detailed transaction histories.

- **Product Inventory:**
  Added functionality to manage product details, track inventory levels, and generate reports on product sales and availability.

- **Customer Management:**
  Improved customer management features, including maintaining customer profiles, tracking purchases, and managing outstanding balances.

- **Employee Administration:**
  Introduced an employee management system to oversee roles, responsibilities, and access levels within the application.

- **Security Enhancements:**
  Implemented robust security measures, including password encryption using SHA-256, to safeguard credentials and sensitive information.

## Module Screenshots
- Module 1:
![image](https://github.com/user-attachments/assets/0f1015f9-3e33-4567-b123-23074fb7c3a5)

- Module 2:
  ![image](https://github.com/user-attachments/assets/e91a15be-bc98-4a54-b203-0c931a6342ec)

- Module 3:
  ![image](https://github.com/user-attachments/assets/2ae2ce78-e8bc-4d8e-8cb3-11d31089b440)
  
- Module 4:
  ![image](https://github.com/user-attachments/assets/7d682237-e8a6-4fdc-8c11-8698d883fb1f)

- Module 5:
  ![image](https://github.com/user-attachments/assets/5f016b71-f58f-4860-8683-afe98a16f219)

- Module 6:
  ![image](https://github.com/user-attachments/assets/5e6385f8-2734-4a1f-876f-d7a0b9f8b0a1)
  
- Module 7:
  ![image](https://github.com/user-attachments/assets/5742798d-72f3-4e9b-abea-29149afb5790)




## Future Work

- **Enhanced Customer Notifications:** Implementing improved notifications for customers regarding invoice status updates, payment reminders, and promotional offers to enhance customer engagement and service.

- **Employee Tracking in Invoices:** To enhance accountability and track employee activities, future iterations of the project will include storing the Employee ID with each new invoice. This feature aims to provide insights into employee performance, workload distribution, and customer interactions, facilitating better operational management and transparency within the organization.

# THANK YOU!!
