package controller;

import model.Book;
import service.BookService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @Path("/book/{id}")
    @GET
    public Book getBookById(@PathParam(value = "id") int id) {
        return bookService.getBookById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Book addBook(Book book) {
        return bookService.addBook(book);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateBook(Book book) {
        bookService.updateBook(book);
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteBook(@PathParam(value = "id") int id) {
        bookService.deleteBook(id);
    }
}
