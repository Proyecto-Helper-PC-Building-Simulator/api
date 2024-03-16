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
    @Query("SELECT c FROM Component c JOIN FETCH c.manufacturer m JOIN FETCH c.lighting l JOIN FETCH c.componentType ORDER BY c.name ASC")
    Page<Component> findAll(@NonNull Pageable pageable);

    @Query("SELECT c FROM Component c JOIN FETCH c.componentType ct JOIN FETCH c.manufacturer m JOIN FETCH c.lighting l WHERE LOWER(ct.name) = LOWER(:componentTypeName) ORDER BY c.name ASC")
    Page<Component> findAllByComponentType(String componentTypeName, Pageable pageable);

    @Query("SELECT c FROM Component c JOIN FETCH c.componentType ct JOIN FETCH c.manufacturer m JOIN FETCH c.lighting l WHERE LOWER (c.name) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(ct.name) = LOWER(:componentTypeName) ORDER BY c.name ASC")
    Page<Component> findAllByComponentTypeAndName(String componentTypeName, String name, Pageable pageable);
}
