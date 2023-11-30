package es.bit.api.persistence.repository.jpa;

import es.bit.api.persistence.model.CableType;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICableTypeJPARepository extends JpaRepository<CableType, Integer> {
    @NonNull
    Page<CableType> findAll(@NonNull Pageable pageable);
}
