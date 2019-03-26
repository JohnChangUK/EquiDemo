package service;

import model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {

    private Map<Integer, Book> bookMap = new HashMap<>();
    private List<Book> listOfBooks = new ArrayList<>();

    public BookService() {
        Book effectiveJava = new Book(1, "Effective Java", 400);
        Book hibernateInAction = new Book(2, "Hibernate in action", 300);
        Book learningPython = new Book(3, "Learning Python", 250);
        Book springInAction = new Book(4, "Spring in action", 500);

        bookMap.put(1, effectiveJava);
        bookMap.put(2, hibernateInAction);
        bookMap.put(3, learningPython);
        bookMap.put(4, springInAction);

        listOfBooks.addAll(bookMap.values());
    }

    public List<Book> getBooks() {
        return listOfBooks;
    }

    public Book getBookById(int id) {
        return bookMap.get(id);
    }

    public Book addBook(Book book) {
        book.setId(getMaxId());
        bookMap.put(book.getId(), book);
        return book;
    }

    public void updateBook(Book book) {
        int id = book.getId();
        bookMap.put(id, book);
    }

    public void deleteBook(int id) {
        bookMap.remove(id);
    }

    private int getMaxId() {
        int max = 0;
        for (int id : bookMap.keySet()) {
            if (max <= id) {
                max = id;
            }
        }

        return max;
    }
}
