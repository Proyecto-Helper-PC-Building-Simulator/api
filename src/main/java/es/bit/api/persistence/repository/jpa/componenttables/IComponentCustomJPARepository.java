package es.bit.api.persistence.repository.jpa.componenttables;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IComponentCustomJPARepository<T> {
    List<T> findAll(String search);
}
