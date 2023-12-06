package es.bit.api.persistence.repository.jpa;

import es.bit.api.persistence.model.CpuSocket;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICpuSocketJPARepository extends JpaRepository<CpuSocket, Integer> {
    @NonNull
    Page<CpuSocket> findAll(@NonNull Pageable pageable);
}
