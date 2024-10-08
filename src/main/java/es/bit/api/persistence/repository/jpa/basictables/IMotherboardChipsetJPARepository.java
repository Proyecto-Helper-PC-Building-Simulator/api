package es.bit.api.persistence.repository.jpa.basictables;

import es.bit.api.persistence.model.basictables.MotherboardChipset;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMotherboardChipsetJPARepository extends JpaRepository<MotherboardChipset, Integer> {
    @NonNull
    Page<MotherboardChipset> findAll(@NonNull Pageable pageable);
}
