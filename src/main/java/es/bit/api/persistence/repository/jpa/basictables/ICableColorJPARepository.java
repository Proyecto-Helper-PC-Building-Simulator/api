package es.bit.api.persistence.repository.jpa.basictables;

import es.bit.api.persistence.model.basictables.CableColor;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICableColorJPARepository extends JpaRepository<CableColor, Integer> {
    @NonNull
    Page<CableColor> findAll(@NonNull Pageable pageable);
}
