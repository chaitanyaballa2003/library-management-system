# Library Management System

A web-based Library Management System built with **Java Servlets, JDBC and MySQL**. It lets a librarian add, view, search, edit and delete books from a simple browser interface.

## Features

- **Add** a new book (name, author, type, price)
- **View** all books in a table with a total count
- **Search** books by name, author or book type
- **Edit** the details of an existing book
- **Delete** a book (with a confirmation prompt)
- Input validation and friendly success / error messages
- Clean, responsive UI using plain HTML and CSS

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java (JDK 17 recommended) |
| Web | Servlets (Jakarta Servlet 6.0) |
| Database access | JDBC with `PreparedStatement` |
| Database | MySQL |
| Frontend | HTML5, CSS3 |
| Server | Apache Tomcat 10.1 |
| IDE | Eclipse (Dynamic Web Project) |

## Project Structure

```
LibraryManagement
├── schema.sql                         # Database and table creation script
└── src/main
    ├── java/com/library
    │   ├── model/Book.java            # Book entity (id, bname, author, booktype, price)
    │   ├── util/DBConnection.java     # JDBC connection
    │   ├── dao/BookDAO.java           # All SQL operations (add, list, search, update, delete)
    │   └── servlet/BookServlet.java   # Request handling and HTML output
    └── webapp
        ├── index.html                 # Home page
        ├── css/style.css              # Styling
        └── WEB-INF/lib                # MySQL Connector/J jar
```

## How It Works

1. The browser sends a request to `/books` (GET to view pages, POST to add, update or delete).
2. `BookServlet` reads the request, validates the input and calls `BookDAO`.
3. `BookDAO` gets a connection from `DBConnection` and runs the SQL using `PreparedStatement`.
4. The servlet renders the result as an HTML page, or redirects after a POST so a page refresh does not submit the form twice.

## Database Setup

Run `schema.sql` in MySQL (Workbench or command line):

```sql
CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE IF NOT EXISTS books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    bname VARCHAR(100) NOT NULL,
    author VARCHAR(100),
    booktype VARCHAR(100),
    price DECIMAL(10,2)
);
```

## Configuration

Open `src/main/java/com/library/util/DBConnection.java` and set your own MySQL details:

```java
private static final String URL = "jdbc:mysql://localhost:3306/library_db";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

## Running the Project (Eclipse)

1. Install **JDK 17+**, **MySQL** and **Apache Tomcat 10.1**.
2. In Eclipse: **File → Import → Existing Projects into Workspace** and select this folder.
3. Make sure the project uses **Apache Tomcat v10.1** as its target runtime (**Project → Properties → Targeted Runtimes**).
4. Confirm the MySQL Connector/J jar is present in `src/main/webapp/WEB-INF/lib`.
5. Right click the project and choose **Run As → Run on Server** (Tomcat v10.1).
6. Open `http://localhost:8080/LibraryManagement/` in your browser (use your own Tomcat port if it is different, for example 8081).

> **Note:** Tomcat 10 and above use the `jakarta.servlet` package. Tomcat 9 and below use `javax.servlet`, so this project will not run on Tomcat 9 without changing the imports.

## Key Concepts Used

- **MVC-style layering:** model, DAO, servlet and view are kept separate
- **DAO pattern** to keep all SQL out of the servlet
- **`PreparedStatement`** to prevent SQL injection
- **HTML escaping** of user input to prevent XSS
- **try-with-resources** so connections and statements are always closed
- **Post/Redirect/Get** to avoid duplicate form submissions

## Future Improvements

- Admin login with sessions
- Members table and book issue / return with due dates and fines
- Pagination for the book list
- JSP views instead of printing HTML from the servlet
- Connection pooling and externalised configuration

## Author

**Chaitanya Satya Bhimesh Balla**
GitHub: [chaitanyaballa2003](https://github.com/chaitanyaballa2003)
LinkedIn: [chaitanya-balla28](https://www.linkedin.com/in/chaitanya-balla28/)
