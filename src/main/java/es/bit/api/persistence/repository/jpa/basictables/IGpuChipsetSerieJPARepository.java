package es.bit.api.persistence.repository.jpa.basictables;

import es.bit.api.persistence.model.basictables.GpuChipsetSerie;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGpuChipsetSerieJPARepository extends JpaRepository<GpuChipsetSerie, Integer> {
    @NonNull
    Page<GpuChipsetSerie> findAll(@NonNull Pageable pageable);
}
