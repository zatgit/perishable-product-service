package com.zmart.api.product.repository;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("java:S119")
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    public BaseRepositoryImpl(final Class<T> domainClass, final EntityManager entityManager) {
        super(domainClass, entityManager);
    }

    public BaseRepositoryImpl(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager)  {
        super(entityInformation, entityManager);
    }

    @Override
    public T findBy(final Specification<T> spec) {
        try {
            return getQuery(spec, Sort.unsorted()).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public T findBy(final Specification<T> spec, final int offset, final int maxResults) {
        try {
            return getTypedQuery(spec, offset, maxResults, Sort.unsorted()).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<T> findAll(final Specification<T> spec, final int offset, final int maxResults) {
        return getTypedQuery(spec, offset, maxResults, Sort.unsorted()).getResultList();
    }

    @Override
    public List<T> findAll(@Nullable final Specification<T> spec, final int offset, final int maxResults, final Sort sort) {
        return getTypedQuery(spec, offset, maxResults, sort).getResultList();
    }

    private TypedQuery<T> getTypedQuery(@Nullable Specification<T> spec, int offset, int maxResults, Sort sort) {
        validateOffsetAndMaxResults(offset, maxResults);
        return getQuery(spec, sort).setFirstResult(offset).setMaxResults(maxResults);
    }

    private void validateOffsetAndMaxResults(int offset, int maxResults) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset must not be less than zero");
        }
        if (maxResults < 1) {
            throw new IllegalArgumentException("Max results must not be less than one");
        }
    }
}
