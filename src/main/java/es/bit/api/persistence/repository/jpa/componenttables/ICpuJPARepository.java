package es.bit.api.persistence.repository.jpa.componenttables;

import es.bit.api.persistence.model.componenttables.Cpu;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICpuJPARepository extends JpaRepository<Cpu, Integer> {
    @NonNull
    Page<Cpu> findAll(@NonNull Pageable pageable);
}
