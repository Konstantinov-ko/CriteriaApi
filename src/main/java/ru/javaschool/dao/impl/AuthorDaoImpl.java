package ru.javaschool.dao.impl;


import org.springframework.stereotype.Repository;
import ru.javaschool.dao.api.AuthorDao;
import ru.javaschool.model.Author;
import ru.javaschool.model.Author_;
import ru.javaschool.model.Book;
import ru.javaschool.model.Book_;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository("authorDao")
public class AuthorDaoImpl extends GenericDaoImpl<Author, Long> implements AuthorDao {
    /**
     * Get author list with filter by amount (Subquery, multiselect, count, groupBy, orderBy)
     *
     * @param amount - books amount for where clause
     * @return - object array. The first element is author and th second is books amount
     *
     * SELECT *
     * FROM author
     * WHERE (SELECT COUNT(*)
     *      FROM book
     *      WHERE author.id = book.author_id
     *      GROUP BY book.author_id) > ?
     * ORDER BY author.surname ASC
     */
    @Override
    public List<Author> getAuthorsByBooksAmount(Long amount) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        // From
        Root<Author> authorRoot = criteriaQuery.from(Author.class);

        // Subquery returns books amount
        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        // Sub from
        Root<Book> subBookRoot = subquery.from(Book.class);
        // Sub where
        subquery.where(criteriaBuilder.equal(
                subBookRoot.get(Book_.author_id), authorRoot.get(Author_.id)));
        // Sub groupBy
        subquery.groupBy(subBookRoot.get(Book_.author_id));

        // Select
        criteriaQuery.select(authorRoot);
        // Where
        criteriaQuery.where(criteriaBuilder.gt(subquery.select(criteriaBuilder.count(subBookRoot)), amount));
        // Order
        criteriaQuery.orderBy(criteriaBuilder.asc(authorRoot.get(Author_.surname)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Get all authors with books amount (multiselect, join, groupBy, orderBy)
     *
     * @return Authors with books amount
     *
     * SELECT *, COUNT(*)
     * FROM book
     * INNER JOIN author on author.id = book.author_id
     * GROUP BY book.author_id
     * ORDER BY COUNT(*) DESC
     */
    @Override
    public List<Object[]> getAllAuthorsAndBooksAmount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        // from
        Root<Book> rootBook = criteriaQuery.from(Book.class);
        // Join
        Join<Book, Author> rootAuthor = rootBook.join(Book_.author, JoinType.INNER);
        // select
        criteriaQuery.multiselect(rootAuthor, criteriaBuilder.count(rootBook));
        // GroupBy
        criteriaQuery.groupBy(rootBook.get(Book_.author_id));
        // OrderBy
        criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.count(rootBook)));
        // get result
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
