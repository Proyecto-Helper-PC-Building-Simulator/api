package es.bit.api.persistence.repository.jpa;

import es.bit.api.persistence.model.MultiGpuType;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMultiGpuTypeJPARepository extends JpaRepository<MultiGpuType, Integer> {
    @NonNull
    Page<MultiGpuType> findAll(@NonNull Pageable pageable);
}
