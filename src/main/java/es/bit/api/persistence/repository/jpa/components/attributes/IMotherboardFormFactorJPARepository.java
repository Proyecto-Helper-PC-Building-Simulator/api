package es.bit.api.persistence.repository.jpa.components.attributes;

import es.bit.api.persistence.model.components.attributes.MotherboardFormFactor;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMotherboardFormFactorJPARepository extends JpaRepository<MotherboardFormFactor, Integer> {
    @NonNull
    Page<MotherboardFormFactor> findAll(@NonNull Pageable pageable);
}
