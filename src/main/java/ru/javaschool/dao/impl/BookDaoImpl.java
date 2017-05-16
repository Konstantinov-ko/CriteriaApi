package ru.javaschool.dao.impl;

import org.springframework.stereotype.Repository;
import ru.javaschool.dao.api.BookDao;
import ru.javaschool.model.Author;
import ru.javaschool.model.Book;

import javax.persistence.criteria.*;
import java.util.List;

@Repository("bookDao")
public class BookDaoImpl extends GenericDaoImpl<Book, Long> implements BookDao {

}
