package es.bit.api.persistence.repository.jpa;

import es.bit.api.persistence.model.CaseFanSize;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICaseFanSizeJPARepository extends JpaRepository<CaseFanSize, Integer> {
    @NonNull
    Page<CaseFanSize> findAll(@NonNull Pageable pageable);
}
