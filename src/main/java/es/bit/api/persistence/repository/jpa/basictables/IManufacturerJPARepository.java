package es.bit.api.persistence.repository.jpa.basictables;

import es.bit.api.persistence.model.basictables.Manufacturer;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IManufacturerJPARepository extends JpaRepository<Manufacturer, Integer> {
    @NonNull
    Page<Manufacturer> findAll(@NonNull Pageable pageable);
}
