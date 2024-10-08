package es.bit.api.persistence.repository.jpa.basictables;

import es.bit.api.persistence.model.basictables.Lighting;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILightingJPARepository extends JpaRepository<Lighting, Integer> {
    @NonNull
    Page<Lighting> findAll(@NonNull Pageable pageable);
}
