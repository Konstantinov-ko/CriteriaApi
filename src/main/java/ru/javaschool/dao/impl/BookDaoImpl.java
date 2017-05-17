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
    /**
     * Get books by filter (Join + equal and like where)
     *
     * @param bookFilter - filter with name, surname and year
     * @return books
     *
     * SELECT book
     * FROM book
     * INNER JOIN author on author.id = book.author_id
     * WHERE author.name LIKE %?% AND author.surname LIKE %?% AND book.year = ?
     * ORDER BY book.year ASC
     */
    @Override
    public List<Book> getBooksByFilter(BookFilter bookFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        // from
        Root<Book> rootBook = criteriaQuery.from(Book.class);
        // select
        criteriaQuery.select(rootBook);
        Join<Book, Author> rootAuthor = rootBook.join(Book_.author, JoinType.INNER);
        // where
        List<Predicate> predicates = new ArrayList<>();
        if (bookFilter.getName() != null) {
            predicates.add(criteriaBuilder.like(
                    rootAuthor.get(Author_.name), "%" + bookFilter.getName() + "%"));
        }
        if (bookFilter.getSurname() != null) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(
                    rootAuthor.get(Author_.surname), "%" + bookFilter.getSurname() + "%")));
        }
        if (bookFilter.getYear() != null) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(
                    rootBook.get(Book_.year), bookFilter.getYear()
            )));
        }
        if (predicates.size() > 0) criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        criteriaQuery.orderBy(criteriaBuilder.asc(rootBook.get(Book_.year)));
        // get result
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
