package es.bit.api.persistence.repository.jpa.components;

import es.bit.api.persistence.model.components.Cpu;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ICpuJpaRepository extends IGenericJpaRepository<Cpu, Integer> {
    @Query("SELECT MIN(c.wattage) as minWattage, MAX(c.wattage) as maxWattage FROM Cpu c")
    Map<String, Integer> findWattageRange();

    @Query("SELECT DISTINCT c.cores FROM Cpu c ORDER BY cores ASC")
    List<Integer> findDistinctCores();

    @Query("SELECT MIN(c.frequency) as minFrequency, MAX(c.frequency) as maxFrequency FROM Cpu c")
    Map<String, Integer> findFrequencyRange();

    @Query("SELECT DISTINCT c.cpuSocket.name FROM Cpu c")
    List<String> findDistinctCpuSockets();

    @Query("SELECT DISTINCT c.cpuSerie.name FROM Cpu c")
    List<String> findDistinctCpuSeries();
}
