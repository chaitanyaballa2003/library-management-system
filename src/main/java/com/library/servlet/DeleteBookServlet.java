package com.library.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.library.dao.BookDAO;


@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public DeleteBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private BookDAO dao = new BookDAO();
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idText = request.getParameter("id");
		
		if(idText == null || idText.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book ID is required");
			return;
		}
		try {
			int id =Integer.parseInt(idText);
			
			boolean deleted = dao.deleteBook(id);

			if (deleted) {
			    response.sendRedirect(request.getContextPath() + "/viewBooks");
			} else {
			    response.sendError(
			        HttpServletResponse.SC_NOT_FOUND,"Book not found");
			}
		}catch(NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Please enter a valid Book ID");
		}catch(SQLException e) {
			throw new ServletException("Unable to delete book", e);
		}
		
	}

}
