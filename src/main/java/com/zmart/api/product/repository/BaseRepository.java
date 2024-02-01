package com.zmart.api.product.repository;

import jakarta.annotation.Nullable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("java:S119")
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    T findBy(Specification<T> spec);
    T findBy(Specification<T> spec, int offset, int maxResults);
    List<T> findAll(@Nullable Specification<T> spec, int offset, int maxResults);
    List<T> findAll(@Nullable Specification<T> spec, int offset, int maxResults, Sort sort);
}