# Invoice Management Application

This Java application is designed to manage invoices, customer details, product details, and employee details in a business environment like grocery shop. It operates entirely in the terminal and interacts with a PostgreSQL database to store and retrieve information using JDBC(Java Database Connectivity).

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

### ProductDetails
- **productId**: SERIAL PRIMARY KEY
- **productName**: VARCHAR(100) NOT NULL
- **productPrice**: DECIMAL(10, 2) NOT NULL
- **totalSoldAmount**: NUMERIC(10, 2)
- **availableProducts**: INT
  
### CustomerDetails
- **customerId**: SERIAL PRIMARY KEY
- **customerName**: VARCHAR(100) NOT NULL
- **customerAddress**: VARCHAR(255)
- **customerEmail**: VARCHAR(100) UNIQUE
- **totalDue**: DECIMAL(10, 2)
- **phoneNumber**: VARCHAR(15) UNIQUE
  
### InvoiceProducts
- **invoiceProductId**: SERIAL PRIMARY KEY
- **invoiceId**: INT NOT NULL
- **productId**: INT NOT NULL
- **quantity**: INT NOT NULL
- **unitPrice**: DECIMAL(10, 2) NOT NULL
- **FOREIGN KEY** (invoiceId) REFERENCES InvoiceDetails(invoiceId)
- **FOREIGN KEY** (productId) REFERENCES ProductDetails(productId)
  
### EmployeeDetails
- **empId**: SERIAL PRIMARY KEY
- **empName**: VARCHAR(100) NOT NULL
- **empEmail**: VARCHAR(100) UNIQUE
- **role**: VARCHAR(50)

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
  Implemented robust security measures, including password encryption using SHA-256, to safeguard user credentials and sensitive information.
