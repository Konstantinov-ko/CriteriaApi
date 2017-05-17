package ru.javaschool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.javaschool.dao.api.AuthorDao;
import ru.javaschool.model.Author;

import java.util.List;

@RestController
public class AuthorController {
    @Autowired
    AuthorDao authorDao;

    @RequestMapping(value = "/author/create/", method = RequestMethod.POST)
    public Author createAuthor(@RequestBody Author author) {
        authorDao.create(author);
        return author;
    }

    @RequestMapping(value = "/author/createList/", method = RequestMethod.POST)
    public List<Author> createAuthorList(@RequestBody List<Author> authorList) {
        authorList.forEach(author -> authorDao.create(author));
        return authorList;
    }

    @RequestMapping(value = "/author/get/", method = RequestMethod.GET)
    public Author getAuthor(@RequestParam("id") Long id) {
        return authorDao.get(id);
    }

    @RequestMapping(value = "/author/getAll/", method = RequestMethod.GET)
    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }

    @RequestMapping(value = "/author/getAuthorsByBooksAmount/", method = RequestMethod.GET)
    public List<Author> getAuthorsByBooksAmount(@RequestParam("amount") Long amount) {
        return authorDao.getAuthorsByBooksAmount(amount);
    }

    @RequestMapping(value = "/author/getAllAuthorsAndBooksAmount/", method = RequestMethod.GET)
    public List<Object[]> getAllAuthorsAndBooksAmount() {
        return authorDao.getAllAuthorsAndBooksAmount();
    }
}
