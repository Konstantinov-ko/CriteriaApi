package ru.javaschool.dao.api;

import ru.javaschool.model.Author;

import java.util.List;

public interface AuthorDao extends GenericDao<Author, Long> {
    List<Author> getAuthorList();
}