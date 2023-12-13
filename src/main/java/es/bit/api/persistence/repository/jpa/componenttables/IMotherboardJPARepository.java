package es.bit.api.persistence.repository.jpa.componenttables;

import es.bit.api.persistence.model.componenttables.Motherboard;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMotherboardJPARepository extends JpaRepository<Motherboard, Integer> {
    @NonNull
    Page<Motherboard> findAll(@NonNull Pageable pageable);
}
