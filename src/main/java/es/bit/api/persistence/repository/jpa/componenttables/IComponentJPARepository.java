package es.bit.api.persistence.repository.jpa.componenttables;

import es.bit.api.persistence.model.componenttables.Component;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComponentJPARepository extends JpaRepository<Component, Integer> {
    @NonNull
    Page<Component> findAll(@NonNull Pageable pageable);
}
