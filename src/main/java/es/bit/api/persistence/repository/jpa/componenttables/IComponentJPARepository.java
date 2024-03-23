package es.bit.api.persistence.repository.jpa.componenttables;

import es.bit.api.persistence.model.componenttables.Component;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IComponentJPARepository extends JpaRepository<Component, Integer> {
    @NonNull
    @Query("SELECT c FROM Component c JOIN FETCH c.manufacturer m JOIN FETCH c.lighting l JOIN FETCH c.componentType WHERE c.componentId = :componentId")
    Optional<Component> findById(Integer componentId);

    @NonNull
    @Query("SELECT c FROM Component c JOIN FETCH c.manufacturer m JOIN FETCH c.lighting l JOIN FETCH c.componentType ORDER BY c.componentId ASC")
    Page<Component> findAll(@NonNull Pageable pageable);
}
