package com.cmgeorges.examen.mvc.employees.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PaginationRepository< T, ID > extends CrudRepository< T, ID > {


    /**
     * sorting
     * @param sort
     * @return
     */
    Iterable < T > findAll(Sort sort);


    /**
     * paging
     * @param pageable
     * @return
     */
    Page< T > findAll(Pageable pageable);
}
