package es.bit.api.persistence.repository.jpa.componenttables;

import es.bit.api.persistence.model.componenttables.CaseFan;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICaseFanJPARepository extends JpaRepository<CaseFan, Integer> {
    @NonNull
    Page<CaseFan> findAll(@NonNull Pageable pageable);
}
