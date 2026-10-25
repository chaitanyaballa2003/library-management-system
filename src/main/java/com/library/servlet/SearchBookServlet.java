
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

@WebServlet("/searchBook")
public class SearchBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private BookDAO dao = new BookDAO();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String keyword = request.getParameter("keyword");

        try {
            List<Book> books = dao.searchBooks(keyword);

            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Search Results</title>");
            out.println("<link rel='stylesheet' href='"
                    + request.getContextPath() + "/css/style.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1>Search Results</h1>");

            if (books.isEmpty()) {
                out.println("<p>No books found.</p>");
            } else {
                out.println("<table border='1'>");
                out.println("<tr>");
                out.println("<th>ID</th>");
                out.println("<th>Book Name</th>");
                out.println("<th>Author</th>");
                out.println("<th>Book Type</th>");
                out.println("<th>Price</th>");
                out.println("</tr>");

                for (Book book : books) {
                    out.println("<tr>");
                    out.println("<td>" + book.getId() + "</td>");
                    out.println("<td>" + book.getBname() + "</td>");
                    out.println("<td>" + book.getAuthor() + "</td>");
                    out.println("<td>" + book.getBooktype() + "</td>");
                    out.println("<td>" + book.getPrice() + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
            }

            out.println("<br>");
            out.println("<a href='" + request.getContextPath()
                    + "/searchBook.html'>Search Again</a>");
            out.println("<br><br>");
            out.println("<a href='" + request.getContextPath()
                    + "/index.html'>Back to Home</a>");

            out.println("</body>");
            out.println("</html>");

        } catch (SQLException e) {
            throw new ServletException("Unable to search books", e);
        }
    }
}
