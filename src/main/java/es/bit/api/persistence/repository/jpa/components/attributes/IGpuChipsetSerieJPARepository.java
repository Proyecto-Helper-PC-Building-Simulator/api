package es.bit.api.persistence.repository.jpa.components.attributes;

import es.bit.api.persistence.model.components.attributes.GpuChipsetSerie;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGpuChipsetSerieJPARepository extends JpaRepository<GpuChipsetSerie, Integer> {
    @NonNull
    Page<GpuChipsetSerie> findAll(@NonNull Pageable pageable);
}
