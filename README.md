# Library Management System

## About the Project
The Library Management System is a Java web application developed using Servlets, JDBC, and MySQL. It helps manage library book records and provides a simple interface for interacting with the database.

## Technologies Used
- Java
- Java Servlets
- JDBC
- MySQL
- HTML
- Apache Tomcat
- MySQL Connector/J

## Features
- Add and manage book records
- Store book information in a MySQL database
- Connect the Java application to MySQL using JDBC
- Web interface built with HTML

## Project Structure
- `src/main/java/com/library/dao/` – Database operations
- `src/main/java/com/library/model/` – Book model
- `src/main/java/com/library/servlet/` – Servlet handling
- `src/main/java/com/library/util/` – Database connection
- `src/main/webapp/` – Web pages and web resources
- `schema.sql` – Database schema

## Setup Instructions
1. Install the JDK, MySQL, and Apache Tomcat.
2. Create the required database using `schema.sql`.
3. Configure your database credentials in `DBConnection.java`.
4. Add the MySQL Connector/J library to the project.
5. Deploy the application to Tomcat.
6. Open the application in your browser.

## Purpose
This project was developed to practice Java web development, JDBC database connectivity, and Servlet-based application development.
