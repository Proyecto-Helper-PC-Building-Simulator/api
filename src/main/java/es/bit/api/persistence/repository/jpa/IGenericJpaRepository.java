package es.bit.api.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface IGenericJpaRepository<T, I extends Serializable> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {
    @Query("SELECT DISTINCT c.manufacturer.name FROM Component c WHERE c.componentType.nameIdentifier = :type")
    List<String> findDistinctManufacturersByType(@Param("type") String type);

    @Query("SELECT MIN(c.price) as minPrice, MAX(c.price) as maxPrice FROM Component c WHERE c.componentType.nameIdentifier = :type")
    Map<String, Double> findPriceRangeByType(@Param("type") String type);
}