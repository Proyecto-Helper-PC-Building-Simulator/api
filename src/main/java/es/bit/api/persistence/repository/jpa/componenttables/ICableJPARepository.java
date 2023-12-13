package es.bit.api.persistence.repository.jpa.componenttables;

import es.bit.api.persistence.model.componenttables.Cable;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICableJPARepository extends JpaRepository<Cable, Integer> {
    @NonNull
    Page<Cable> findAll(@NonNull Pageable pageable);
}
