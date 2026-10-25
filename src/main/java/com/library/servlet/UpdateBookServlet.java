package com.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.library.dao.BookDAO;
import com.library.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/updateBook")
public class UpdateBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private BookDAO dao = new BookDAO();
    public UpdateBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		String idText = request.getParameter("id");
		
		if (idText ==null || idText.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book ID is Required");
			return;
		}
		try {
			int id =Integer.parseInt(idText);
			Book book =dao.getBookById(id);
			
			PrintWriter out = response.getWriter();
			 out.println("<!DOCTYPE html>");
	            out.println("<html>");
	            out.println("<head>");
	            out.println("<title>Edit Book</title>");
	            out.println("<link rel='stylesheet' href='"
	                    + request.getContextPath() + "/css/style.css'>");
	            out.println("</head>");
	            out.println("<body>");
	            out.println("<h1>Edit Book Details</h1>");

	            if (book == null) {
	                out.println("<p>Book not found.</p>");
	            } else {
	                out.println("<form action='" + request.getContextPath()
	                        + "/updateBook' method='post'>");

	                out.println("<input type='hidden' name='id' value='"
	                        + book.getId() + "'>");

	                out.println("<label>Book Name:</label>");
	                out.println("<input type='text' name='bname' value='"
	                        + book.getBname() + "' required><br><br>");

	                out.println("<label>Author:</label>");
	                out.println("<input type='text' name='author' value='"
	                        + book.getAuthor() + "'><br><br>");

	                out.println("<label>Book Type:</label>");
	                out.println("<input type='text' name='booktype' value='"
	                        + book.getBooktype() + "'><br><br>");

	                out.println("<label>Price:</label>");
	                out.println("<input type='number' name='price' min='0' step='0.01' value='"
	                        + book.getPrice() + "' required><br><br>");

	                out.println("<button type='submit'>Update Book</button>");
	                out.println("</form>");
		}
		
	            out.println("<br><a href='" + request.getContextPath()
                + "/index.html'>Back to Home</a>");
        out.println("</body></html>");
		}catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid Book ID");
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve book", e);
        }
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		try {
			int id =Integer.parseInt(request.getParameter("id"));
			String bname = request.getParameter("bname");
			String author = request.getParameter("author");
			String booktype = request.getParameter("booktype");
			double price = Double.parseDouble(request.getParameter("price"));
			
			Book book = new Book(id, bname, author, booktype, price);
			dao.updateBook(book);
			
			response.sendRedirect(request.getContextPath()+"/viewBooks");
		}
		catch(NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Please Enter a Valid ID & Price");
		}catch(SQLException e ) {
			throw new ServletException("Unable to update Book", e);
		}
	}
}
