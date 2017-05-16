package ru.javaschool.dao.impl;


import org.springframework.stereotype.Repository;
import ru.javaschool.dao.api.AuthorDao;
import ru.javaschool.model.Author;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("authorDao")
public class AuthorDaoImpl extends GenericDaoImpl<Author, Long> implements AuthorDao {
    @Override
    public List<Author> getAuthorList() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = criteriaQuery.from(Author.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    /*void test() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Author> updateQuery = criteriaBuilder.createCriteriaUpdate(Author.class);
    }*/
}
