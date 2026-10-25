package com.library.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
 
import com.library.dao.BookDAO;
import com.library.model.Book;


@WebServlet("/addBook")
public class AddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   BookDAO dao = new BookDAO();
    public AddBookServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	response.sendRedirect(request.getContextPath()+"/addBook.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		String bname = request.getParameter("bname");
		String author = request.getParameter("author");
		String booktype = request.getParameter("booktype");
		String priceText = request.getParameter("price");
 		
		try {
			double price = Double.parseDouble(priceText);
			Book book = new Book(bname, author, booktype, price);
			dao.addBook(book);
			
			response.sendRedirect(request.getContextPath()+"/viewBooks");
		}catch(NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Please enter valid price");
		}catch(SQLException e) {
			throw new ServletException("unable to save the book",e);
		}
		
	}

}
