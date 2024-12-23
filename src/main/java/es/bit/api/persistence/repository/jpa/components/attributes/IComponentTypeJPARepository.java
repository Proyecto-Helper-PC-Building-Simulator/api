package es.bit.api.persistence.repository.jpa.components.attributes;

import es.bit.api.persistence.model.components.attributes.ComponentType;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IComponentTypeJPARepository extends JpaRepository<ComponentType, Integer> {
    @NonNull
    Page<ComponentType> findAll(@NonNull Pageable pageable);

    boolean existsByNameIdentifier(String nameIdentifier);
}
