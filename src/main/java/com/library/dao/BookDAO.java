package com.library.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.model.Book;
import com.library.util.DBConnection;

public class BookDAO {

    public void addBook(Book b) throws SQLException {
        String sql = "INSERT INTO books (bname, author, booktype, price) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, b.getBname());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getBooktype());
            ps.setDouble(4, b.getPrice());
            ps.executeUpdate();
        }
    }


    public List<Book> getAllBooks() throws SQLException {
    List<Book> list = new ArrayList<>();
    String sql = "SELECT * FROM books";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {

            int id = rs.getInt("id");
            String bname = rs.getString("bname");
            String author = rs.getString("author");
            String booktype = rs.getString("booktype");
            double price = rs.getDouble("price");

            Book book = new Book(id, bname, author, booktype, price);
            	list.add(book);
        	}
    	}
    return list;
    }

    public List<Book> searchBooks(String keyword) throws SQLException {
    List<Book> list = new ArrayList<>();
    String sql = "SELECT * FROM books WHERE bname LIKE ? OR author LIKE ? OR booktype LIKE ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        String search = "%" + keyword + "%";
        ps.setString(1, search);
        ps.setString(2, search);
        ps.setString(3, search);
        
       try(ResultSet rs = ps.executeQuery()){

            while (rs.next()) {
                int id = rs.getInt("id");
                String bname = rs.getString("bname");
                String author = rs.getString("author");
                String booktype = rs.getString("booktype");
                double price = rs.getDouble("price");

                Book book = new Book(id, bname, author, booktype, price);
                list.add(book);
            		}
        		}
		}
    return list;
    }

    public Book getBookById(int id) throws SQLException {
    	String sql = "SELECT * FROM books WHERE id = ?";
    	try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int bookId = rs.getInt("id");
                String bname = rs.getString("bname");
                String author = rs.getString("author");
                String booktype = rs.getString("booktype");
                double price = rs.getDouble("price");
                Book book = new Book(bookId, bname, author, booktype, price);
                return book;
            	}
        	}
    	}
    	return null;
	}

    

    public boolean updateBook(Book b) throws SQLException {
        String sql = "UPDATE books SET bname=?, author=?, booktype=?, price=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, b.getBname());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getBooktype());
            ps.setDouble(4, b.getPrice());
            ps.setInt(5, b.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}