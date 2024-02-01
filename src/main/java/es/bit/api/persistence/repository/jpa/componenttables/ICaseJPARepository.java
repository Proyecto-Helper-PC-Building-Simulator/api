package es.bit.api.persistence.repository.jpa.componenttables;

import es.bit.api.persistence.model.componenttables.Case;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICaseJPARepository extends JpaRepository<Case, Integer> {
    @NonNull
    Page<Case> findAll(@NonNull Pageable pageable);
}
