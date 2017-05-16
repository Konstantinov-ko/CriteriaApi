package ru.javaschool.dao.api;

import ru.javaschool.BookFilter;
import ru.javaschool.model.Book;

import java.util.List;

public interface BookDao extends GenericDao<Book, Long> {
    List<Book> getBooksByFilter(BookFilter bookFilter);
}
