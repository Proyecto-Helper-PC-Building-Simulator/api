package es.bit.api.persistence.repository.jpa.basictables;

import es.bit.api.persistence.model.basictables.CpuSerie;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICpuSerieJPARepository extends JpaRepository<CpuSerie, Integer> {
    @NonNull
    Page<CpuSerie> findAll(@NonNull Pageable pageable);
}
