package ru.javaschool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.javaschool.dao.api.BookDao;
import ru.javaschool.model.Book;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    BookDao bookDao;

    @RequestMapping(value = "/book/create/", method = RequestMethod.POST)
    public Book createBook(@RequestBody Book book) {
        bookDao.create(book);
        return book;
    }

    @RequestMapping(value = "/book/createList/", method = RequestMethod.POST)
    public List<Book> createAuthorList(@RequestBody List<Book> bookList) {
        bookList.forEach(book -> bookDao.create(book));
        return bookList;
    }

    @RequestMapping(value = "/book/get/", method = RequestMethod.GET)
    public Book getBook(@RequestParam("id") Long id) {
        return bookDao.get(id);
    }
}
