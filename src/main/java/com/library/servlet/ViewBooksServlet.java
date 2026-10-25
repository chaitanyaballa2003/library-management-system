
package com.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.library.dao.BookDAO;
import com.library.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/viewBooks")
public class ViewBooksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private BookDAO dao = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try {
            List<Book> books = dao.getAllBooks();

            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>View Books</title>");
            out.println("<link rel='stylesheet' href='"
                    + request.getContextPath() + "/css/style.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1>Book List</h1>");
            out.println("<div class='nav-links'>");
            out.println("<a href='index.html'>Home</a>");
            out.println("<a href='addBook.html'>Add Book</a>");
            out.println("<a href='viewBooks'>View Books</a>");
            out.println("<a href='searchBook.html'>Search Book</a>");
            out.println("<a href='updateBook.html'>Update Book</a>");
            out.println("<a href='deleteBook.html'>Delete Book</a>");
            out.println("</div>");
            out.println("<table border='1'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Book Name</th>");
            out.println("<th>Author</th>");
            out.println("<th>Book Type</th>");
            out.println("<th>Price</th>");
            out.println("</tr>");
            out.println("</thead>");
            
            out.println("<tbody>");
            for (Book book : books) {
                out.println("<tr>");
                out.println("<td>" + book.getId() + "</td>");
                out.println("<td>" + book.getBname() + "</td>");
                out.println("<td>" + book.getAuthor() + "</td>");
                out.println("<td>" + book.getBooktype() + "</td>");
                out.println("<td>" + book.getPrice() + "</td>");
                out.println("</tr>");
            }
            
            out.println("</tbody>");
            out.println("</table>");
            out.println("<br>");
            out.println("<a href='" + request.getContextPath()
                    + "/index.html'>Back to Home</a>");
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve books", e);
        }
    }
}
