package es.bit.api.persistence.repository.jpa.componenttables;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IComponentCustomJPARepository<T> {
    Page<T> findAll(String search, Pageable pageable);
}
