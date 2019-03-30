package resource;

import db.BookDAO;
import model.Book;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class BookResource {

    BookDAO bookDao;

    public BookResource(BookDAO bookDao) {
        this.bookDao = bookDao;
    }

    @GET
    public Response getAllBooks() {
        return Response.status(OK).entity(bookDao.getBooks()).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Integer id) {
        return Response.status(OK).entity(bookDao.findById(id)).build();
    }

    @POST
    public Response add(@Valid Book book) {
        bookDao.insert(book);
        return Response.status(OK).entity(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, @Valid Book book) {
        Book updatedBook = new Book(id, book.getTitle(), book.getPages());
        bookDao.update(updatedBook);
        return Response.status(OK).entity(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        bookDao.deleteById(id);
    }

}
