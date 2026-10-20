package com.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.library.dao.BookDAO;
import com.library.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final BookDAO dao = new BookDAO();

    // ---------------- GET: list / search / add form / edit form ----------------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("new".equals(action)) {
                showForm(req, resp, null);
            } else if ("edit".equals(action)) {
                Book b = dao.getBookById(Integer.parseInt(req.getParameter("id")));
                if (b == null) {
                    redirect(req, resp, "msg=" + enc("Book not found"));
                } else {
                    showForm(req, resp, b);
                }
            } else {
                showList(req, resp);
            }
        } catch (Exception e) {
            showError(req, resp, e);
        }
    }

    // ---------------- POST: add / update / delete ----------------
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        try {
            if ("add".equals(action)) {
                Book b = readBook(req);
                if (b == null) {
                    redirect(req, resp, "action=new&msg=" + enc("Please enter valid details"));
                    return;
                }
                dao.addBook(b);
                redirect(req, resp, "msg=" + enc("Book added successfully"));

            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Book b = readBook(req);
                if (b == null) {
                    redirect(req, resp, "action=edit&id=" + id + "&msg=" + enc("Please enter valid details"));
                    return;
                }
                b.setId(id);
                boolean ok = dao.updateBook(b);
                redirect(req, resp, "msg=" + enc(ok ? "Book updated successfully" : "Book not found"));

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean ok = dao.deleteBook(id);
                redirect(req, resp, "msg=" + enc(ok ? "Book deleted" : "Book not found"));

            } else {
                redirect(req, resp, "");
            }
        } catch (Exception e) {
            showError(req, resp, e);
        }
    }

    // ---------------- Pages ----------------
    private void showList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String q = req.getParameter("q");
        boolean searching = q != null && !q.trim().isEmpty();
        List<Book> books = searching ? dao.searchBooks(q.trim()) : dao.getAllBooks();
        String ctx = req.getContextPath();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        header(out, ctx, "Books");

        message(out, req.getParameter("msg"), false);

        out.println("<div class=\"toolbar\">");
        out.println("<h1>Books <span class=\"count\">" + books.size() + "</span></h1>");
        out.println("<form class=\"search\" method=\"get\" action=\"" + ctx + "/books\">");
        out.println("<input type=\"text\" name=\"q\" placeholder=\"Search name / author / type\" value=\""
                + esc(searching ? q.trim() : "") + "\">");
        out.println("<button type=\"submit\" class=\"btn\">Search</button>");
        if (searching) {
            out.println("<a class=\"btn btn-light\" href=\"" + ctx + "/books\">Clear</a>");
        }
        out.println("</form>");
        out.println("<a class=\"btn btn-primary\" href=\"" + ctx + "/books?action=new\">+ Add Book</a>");
        out.println("</div>");

        if (books.isEmpty()) {
            out.println("<p class=\"empty\">No books found.</p>");
        } else {
            out.println("<div class=\"table-wrap\"><table>");
            out.println("<thead><tr><th>ID</th><th>Book Name</th><th>Author</th><th>Type</th>"
                    + "<th>Price</th><th>Actions</th></tr></thead><tbody>");
            for (Book b : books) {
                out.println("<tr>");
                out.println("<td>" + b.getId() + "</td>");
                out.println("<td>" + esc(b.getBname()) + "</td>");
                out.println("<td>" + esc(b.getAuthor()) + "</td>");
                out.println("<td>" + esc(b.getBooktype()) + "</td>");
                out.println("<td>" + String.format("%.2f", b.getPrice()) + "</td>");
                out.println("<td class=\"actions\">");
                out.println("<a class=\"btn btn-small\" href=\"" + ctx + "/books?action=edit&id=" + b.getId() + "\">Edit</a>");
                out.println("<form method=\"post\" action=\"" + ctx + "/books\" onsubmit=\"return confirm('Delete this book?');\">");
                out.println("<input type=\"hidden\" name=\"action\" value=\"delete\">");
                out.println("<input type=\"hidden\" name=\"id\" value=\"" + b.getId() + "\">");
                out.println("<button type=\"submit\" class=\"btn btn-small btn-danger\">Delete</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</tbody></table></div>");
        }
        footer(out);
    }

    // Same form for Add (book == null) and Edit
    private void showForm(HttpServletRequest req, HttpServletResponse resp, Book book) throws IOException {
        boolean edit = book != null;
        String ctx = req.getContextPath();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        header(out, ctx, edit ? "Edit Book" : "Add Book");

        message(out, req.getParameter("msg"), true);

        out.println("<div class=\"card\">");
        out.println("<h1>" + (edit ? "Edit Book" : "Add New Book") + "</h1>");
        out.println("<form method=\"post\" action=\"" + ctx + "/books\">");
        out.println("<input type=\"hidden\" name=\"action\" value=\"" + (edit ? "update" : "add") + "\">");
        if (edit) {
            out.println("<input type=\"hidden\" name=\"id\" value=\"" + book.getId() + "\">");
        }

        out.println("<label for=\"bname\">Book Name *</label>");
        out.println("<input type=\"text\" id=\"bname\" name=\"bname\" maxlength=\"100\" required value=\""
                + (edit ? esc(book.getBname()) : "") + "\">");

        out.println("<label for=\"author\">Author</label>");
        out.println("<input type=\"text\" id=\"author\" name=\"author\" maxlength=\"100\" value=\""
                + (edit ? esc(book.getAuthor()) : "") + "\">");

        out.println("<label for=\"booktype\">Book Type</label>");
        out.println("<input type=\"text\" id=\"booktype\" name=\"booktype\" list=\"types\" maxlength=\"100\" value=\""
                + (edit ? esc(book.getBooktype()) : "") + "\">");
        out.println("<datalist id=\"types\"><option value=\"Programming\"><option value=\"Comic\">"
                + "<option value=\"Novel\"><option value=\"Science\"><option value=\"History\"></datalist>");

        out.println("<label for=\"price\">Price</label>");
        out.println("<input type=\"number\" id=\"price\" name=\"price\" step=\"0.01\" min=\"0\" required value=\""
                + (edit ? String.valueOf(book.getPrice()) : "") + "\">");

        out.println("<div class=\"form-actions\">");
        out.println("<button type=\"submit\" class=\"btn btn-primary\">" + (edit ? "Update Book" : "Save Book") + "</button>");
        out.println("<a class=\"btn btn-light\" href=\"" + ctx + "/books\">Cancel</a>");
        out.println("</div>");
        out.println("</form></div>");
        footer(out);
    }

    private void showError(HttpServletRequest req, HttpServletResponse resp, Exception e) throws IOException {
        e.printStackTrace();
        resp.setStatus(500);
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        header(out, req.getContextPath(), "Error");
        out.println("<div class=\"card\"><h1>Something went wrong</h1>");
        out.println("<p class=\"alert alert-error\">" + esc(String.valueOf(e.getMessage())) + "</p>");
        out.println("<a class=\"btn btn-primary\" href=\"" + req.getContextPath() + "/books\">Back to Books</a></div>");
        footer(out);
    }

    // ---------------- Helpers ----------------
    private Book readBook(HttpServletRequest req) {
        try {
            String bname = req.getParameter("bname");
            if (bname == null || bname.trim().isEmpty()) {
                return null;
            }
            String author = req.getParameter("author");
            String booktype = req.getParameter("booktype");
            double price = Double.parseDouble(req.getParameter("price"));
            if (price < 0) {
                return null;
            }
            return new Book(bname.trim(),
                    author == null ? "" : author.trim(),
                    booktype == null ? "" : booktype.trim(),
                    price);
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    private void redirect(HttpServletRequest req, HttpServletResponse resp, String query) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/books" + (query.isEmpty() ? "" : "?" + query));
    }

    private String enc(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    private String esc(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    private void message(PrintWriter out, String msg, boolean error) {
        if (msg == null || msg.isEmpty()) {
            return;
        }
        out.println("<p class=\"alert " + (error ? "alert-error" : "alert-success") + "\">" + esc(msg) + "</p>");
    }

    private void header(PrintWriter out, String ctx, String title) {
        out.println("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
        out.println("<title>" + esc(title) + " - Library</title>");
        out.println("<link rel=\"stylesheet\" href=\"" + ctx + "/css/style.css\"></head><body>");
        out.println("<header class=\"navbar\"><a class=\"brand\" href=\"" + ctx + "/\">Library Management</a>");
        out.println("<nav><a href=\"" + ctx + "/books\">All Books</a>");
        out.println("<a href=\"" + ctx + "/books?action=new\">Add Book</a></nav></header>");
        out.println("<main class=\"container\">");
    }

    private void footer(PrintWriter out) {
        out.println("</main><footer class=\"footer\">Library Management &middot; Servlets + JDBC + MySQL</footer>");
        out.println("</body></html>");
    }
}