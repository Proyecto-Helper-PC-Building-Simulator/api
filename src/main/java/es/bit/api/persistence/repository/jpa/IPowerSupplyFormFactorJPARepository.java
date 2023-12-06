package es.bit.api.persistence.repository.jpa;

import es.bit.api.persistence.model.PowerSupplyFormFactor;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPowerSupplyFormFactorJPARepository extends JpaRepository<PowerSupplyFormFactor, Integer> {
    @NonNull
    Page<PowerSupplyFormFactor> findAll(@NonNull Pageable pageable);
}
