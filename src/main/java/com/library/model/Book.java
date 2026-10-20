package com.library.model;

public class Book {

    private int id;
    private String bname;
    private String author;
    private String booktype;
    private double price;

    public Book() {
    }

    public Book(String bname, String author, String booktype, double price) {
        this.bname = bname;
        this.author = author;
        this.booktype = booktype;
        this.price = price;
    }

    public Book(int id, String bname, String author, String booktype, double price) {
        this.id = id;
        this.bname = bname;
        this.author = author;
        this.booktype = booktype;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBname() { return bname; }
    public void setBname(String bname) { this.bname = bname; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getBooktype() { return booktype; }
    public void setBooktype(String booktype) { this.booktype = booktype; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Book [id=" + id + ", bname=" + bname + ", author=" + author
                + ", booktype=" + booktype + ", price=" + price + "]";
    }
}