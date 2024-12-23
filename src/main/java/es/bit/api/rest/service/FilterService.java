package es.bit.api.rest.service;

import es.bit.api.persistence.repository.jpa.components.IComponentJpaRepository;
import es.bit.api.persistence.repository.jpa.components.attributes.IComponentTypeJPARepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilterService {
    private final IComponentJpaRepository componentRepository;
    private final IComponentTypeJPARepository componentTypeRepository;


    public FilterService(IComponentJpaRepository componentRepository, IComponentTypeJPARepository componentTypeRepository) {
        this.componentRepository = componentRepository;
        this.componentTypeRepository = componentTypeRepository;
    }


    public Map<String, Object> getFiltersForComponentType(String componentType) {
        // TODO: Implement custom exception
        if (!componentTypeRepository.existsByNameIdentifier(componentType.toLowerCase())) {
            throw new IllegalArgumentException("Invalid component type: " + componentType);
        }

        List<String> manufacturers = componentRepository.findDistinctManufacturersByType(componentType);
        Map<String, Double> priceRange = componentRepository.findPriceRangeByType(componentType);

        Map<String, Object> filters = new HashMap<>();
        filters.put("manufacturers", manufacturers);
        filters.put("priceRange", priceRange);

        return filters;
    }
}
