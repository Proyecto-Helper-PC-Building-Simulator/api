package es.bit.api.rest.service;

import es.bit.api.persistence.repository.jpa.components.IComponentJpaRepository;
import es.bit.api.persistence.repository.jpa.components.ICpuJpaRepository;
import es.bit.api.persistence.repository.jpa.components.attributes.IComponentTypeJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilterService {
    @Autowired
    private IComponentJpaRepository componentRepository;

    @Autowired
    private ICpuJpaRepository cpuRepository;

    @Autowired
    private IComponentTypeJPARepository componentTypeRepository;


    @Cacheable("component_types")
    public List<?> findAllComponentTypes() {
        return this.componentTypeRepository.findAll();
    }

    public Map<String, Object> getFiltersForComponentType(String componentType) {
        // TODO: Implement custom exception
        if (!componentTypeRepository.existsByNameIdentifier(componentType.toLowerCase())) {
            throw new IllegalArgumentException("Invalid component type: " + componentType);
        }

        Map<String, Object> filters = new HashMap<>();

        filters.put("manufacturers", componentRepository.findDistinctManufacturersByType(componentType));
        filters.put("priceRange", componentRepository.findPriceRangeByType(componentType));

        switch (componentType.toLowerCase()) {
            case "cpu":
                Map<String, Integer> wattageRange = cpuRepository.findWattageRange();
                List<Integer> wattageIntervals = generateDynamicRange(wattageRange.get("minWattage"), wattageRange.get("maxWattage"));

                Map<String, Integer> frequencyRange = cpuRepository.findFrequencyRange();
                List<Integer> frequencyIntervals = generateDynamicRange(frequencyRange.get("minFrequency"), frequencyRange.get("maxFrequency"));

                filters.put("wattageIntervals", wattageIntervals);
                filters.put("frequencyIntervals", frequencyIntervals);
                filters.put("sockets", cpuRepository.findDistinctCpuSockets());
                filters.put("series", cpuRepository.findDistinctCpuSeries());
                filters.put("cores", cpuRepository.findDistinctCores());
                break;
        }

        return filters;
    }


    protected List<Integer> generateDynamicRange(int minValue, int maxValue) {
        int interval = (maxValue <= 1000) ? 50 : 500;

        int adjustedMin = (minValue / interval) * interval;
        int adjustedMax = ((maxValue + interval - 1) / interval) * interval;

        List<Integer> range = new ArrayList<>();
        for (int i = adjustedMin; i <= adjustedMax; i += interval) {
            range.add(i);
        }

        return range;
    }
}
