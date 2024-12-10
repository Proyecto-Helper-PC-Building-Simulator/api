package es.bit.api.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface IGenericJpaRepository<T, I extends Serializable> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {
    @Query("SELECT DISTINCT c.manufacturer.name FROM #{#entityName} c")
    List<String> findDistinctManufacturers();

    @Query("SELECT MIN(c.price), MAX(c.price) FROM #{#entityName} c")
    List<Object[]> findMinMaxPrice();

    @Query("SELECT DISTINCT c.lighting.name FROM #{#entityName} c")
    List<String> findDistinctLightings();

    @Query("SELECT MIN(c.level), MAX(c.level) FROM #{#entityName} c")
    List<Object[]> findMinMaxLevel();
}