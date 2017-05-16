package ru.javaschool.dao.impl;


import org.springframework.stereotype.Repository;
import ru.javaschool.dao.api.AuthorDao;
import ru.javaschool.model.Author;

@Repository("authorDao")
public class AuthorDaoImpl extends GenericDaoImpl<Author, Long> implements AuthorDao {
}
