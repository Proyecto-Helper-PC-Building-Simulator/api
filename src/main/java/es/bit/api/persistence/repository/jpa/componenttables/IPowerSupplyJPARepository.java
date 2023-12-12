package es.bit.api.persistence.repository.jpa.componenttables;

import es.bit.api.persistence.model.componenttables.PowerSupply;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPowerSupplyJPARepository extends JpaRepository<PowerSupply, Integer> {
    @NonNull
    Page<PowerSupply> findAll(@NonNull Pageable pageable);
}
