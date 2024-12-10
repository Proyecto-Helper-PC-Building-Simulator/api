package es.bit.api.persistence.repository.jpa.components.attributes;

import es.bit.api.persistence.model.components.attributes.CaseSize;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICaseSizeJPARepository extends JpaRepository<CaseSize, Integer> {
    @NonNull
    Page<CaseSize> findAll(@NonNull Pageable pageable);
}
