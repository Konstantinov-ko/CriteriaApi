package ru.javaschool.dao.impl;

import org.hibernate.engine.internal.JoinSequence;
import org.springframework.stereotype.Repository;
import ru.javaschool.BookFilter;
import ru.javaschool.dao.api.BookDao;
import ru.javaschool.model.Author;
import ru.javaschool.model.Author_;
import ru.javaschool.model.Book;
import ru.javaschool.model.Book_;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository("bookDao")
public class BookDaoImpl extends GenericDaoImpl<Book, Long> implements BookDao {
    @Override
    public List<Book> getBooksByFilter(BookFilter bookFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);

        Join<Book, Author> rootAuthor = root.join(Book_.author, JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        if (bookFilter.getName() != null) predicates.add(criteriaBuilder.equal(rootAuthor.get(Author_.name)
                , bookFilter.getName()));
        if (bookFilter.getSurname() != null) predicates.add(criteriaBuilder.and(
                criteriaBuilder.equal(rootAuthor.get(Author_.surname)
                , bookFilter.getSurname())));

        criteriaQuery.where(predicates.toArray(new Predicate[]{}));

        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
