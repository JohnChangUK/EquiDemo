import db.BookDAO;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import resource.BookResource;

public class Main extends Application<PostgresConfiguration> {

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    @Override
    public void run(PostgresConfiguration configuration, Environment environment) {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(),
                "postgresql");
        final BookDAO bookDAO = jdbi.onDemand(BookDAO.class);
        environment.jersey().register(new BookResource(bookDAO));
    }
}
