package db;

import mapper.BookMapper;
import model.Book;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(BookMapper.class)
public interface BookDAO {

    @SqlQuery("select * from books.book")
    List<Book> getBooks();

    @SqlQuery("select * from books.book where id = :id")
    Book findById(@Bind("id") int id);

    @SqlUpdate("delete from books.book where id = :id")
    int deleteById(@Bind("id") int id);

    @SqlUpdate("update books.book set title = :title, pages = :pages where id = :id")
    int update(@BindBean Book book);

    @SqlUpdate("insert into books.book (id, title, pages) values (:id, :title, :pages)")
    int insert(@BindBean Book book);
}
