package lambda.handler;

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
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/books")
public class BooksResource {

    private BookService bookService;

    public BooksResource(BookService bookService) {
        this.bookService = bookService;
    }

    @Path("/all")
    @GET
    public Response getBooks() {
        return Response.status(OK).entity(bookService.getBooks()).build();
    }

    @Path("/{id}")
    @GET
    public Response getBookById(@PathParam(value = "id") int id) {
        return Response.status(OK).entity(bookService.getBookById(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(Book book) {
        return Response.status(OK).entity(bookService.addBook(book)).build();
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
