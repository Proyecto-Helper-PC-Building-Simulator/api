package es.bit.api.persistence.repository.jpa.componenttables;

import es.bit.api.persistence.model.componenttables.CpuCooler;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICpuCoolerJPARepository extends JpaRepository<CpuCooler, Integer> {
    @NonNull
    Page<CpuCooler> findAll(@NonNull Pageable pageable);
}
