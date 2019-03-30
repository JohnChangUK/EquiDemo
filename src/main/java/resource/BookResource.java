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
import java.util.List;

@Path("/books")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class BookResource {

    BookDAO bookDao;

    public BookResource(BookDAO bookDao) {
        this.bookDao = bookDao;
    }

    @GET
    public List<Book> getAll() {
        return bookDao.getBooks();
    }

    @GET
    @Path("/{id}")
    public Book get(@PathParam("id") Integer id) {
        return bookDao.findById(id);
    }

    @POST
    public Book add(@Valid Book book) {
        bookDao.insert(book);
        return book;
    }

    @PUT
    @Path("/{id}")
    public Book update(@PathParam("id") Integer id, @Valid Book book) {
        Book updatedBook = new Book(id, book.getTitle(), book.getPages());
        bookDao.update(updatedBook);
        return updatedBook;
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        bookDao.deleteById(id);
    }

}
