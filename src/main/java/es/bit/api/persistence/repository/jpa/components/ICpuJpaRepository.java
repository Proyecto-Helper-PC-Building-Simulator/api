package es.bit.api.persistence.repository.jpa.components;

import es.bit.api.persistence.model.components.Cpu;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface ICpuJpaRepository extends IGenericJpaRepository<Cpu, Integer> {
    @Query("SELECT MIN(c.wattage) as minWattage, MAX(c.wattage) as maxWattage FROM Cpu c")
    Map<String, Integer> findWattageRange();
}
