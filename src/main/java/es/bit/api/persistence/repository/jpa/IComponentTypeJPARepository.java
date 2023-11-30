package es.bit.api.persistence.repository.jpa;

import es.bit.api.persistence.model.ComponentType;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComponentTypeJPARepository extends JpaRepository<ComponentType, Integer> {
    @NonNull
    Page<ComponentType> findAll(@NonNull Pageable pageable);
}
