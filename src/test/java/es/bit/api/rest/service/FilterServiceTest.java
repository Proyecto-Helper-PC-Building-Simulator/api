package es.bit.api.rest.service;

import es.bit.api.persistence.repository.jpa.components.IComponentJpaRepository;
import es.bit.api.persistence.repository.jpa.components.ICpuJpaRepository;
import es.bit.api.persistence.repository.jpa.components.attributes.IComponentTypeJPARepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class FilterServiceTest {
    @Mock
    private ICpuJpaRepository cpuRepository;

    @Mock
    private IComponentJpaRepository componentRepository;

    @Mock
    private IComponentTypeJPARepository componentTypeRepository;

    @InjectMocks
    private FilterService filterService;


    @Test
    public void getFiltersForComponentType_validType_shouldReturnFilters() {
        String componentType = "cpu";
        List<String> manufacturers = List.of("Intel", "AMD");
        List<Integer> wattageRangeResult = List.of(0, 50, 100, 150, 200, 250, 300);
        List<Integer> frequencyRangeResult = List.of(2000, 2500, 3000, 3500, 4000, 4500, 5000);
        Map<String, Integer> wattageRange = Map.of("minWattage", 35, "maxWattage", 280);
        Map<String, Integer> frequencyRange = Map.of("minFrequency", 2000, "maxFrequency", 5000);
        Map<String, Double> priceRange = Map.of("minPrice", 100.0, "maxPrice", 1200.0);

        Mockito.when(cpuRepository.findWattageRange()).thenReturn(wattageRange);
        Mockito.when(cpuRepository.findFrequencyRange()).thenReturn(frequencyRange);
        Mockito.when(componentTypeRepository.existsByNameIdentifier(componentType)).thenReturn(true);
        Mockito.when(componentRepository.findDistinctManufacturersByType(componentType)).thenReturn(manufacturers);
        Mockito.when(componentRepository.findPriceRangeByType(componentType)).thenReturn(priceRange);

        Map<String, Object> filters = filterService.getFiltersForComponentType(componentType);

        Assertions.assertEquals(manufacturers, filters.get("manufacturers"));
        Assertions.assertEquals(priceRange, filters.get("priceRange"));
        Assertions.assertEquals(wattageRangeResult, filters.get("wattageIntervals"));
        Assertions.assertEquals(frequencyRangeResult, filters.get("frequencyIntervals"));
    }

    @Test
    void getFiltersForComponentType_invalidType_shouldThrowException() {
        String componentType = "invalidType";

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                filterService.getFiltersForComponentType(componentType)
        );

        Assertions.assertEquals("Invalid component type: " + componentType, exception.getMessage());
    }

    @Test
    void generateDynamicRange_shouldCreateCorrectRanges() {
        List<Integer> range1 = filterService.generateDynamicRange(35, 280);
        Assertions.assertEquals(List.of(0, 50, 100, 150, 200, 250, 300), range1);

        List<Integer> range2 = filterService.generateDynamicRange(2800, 6400);
        Assertions.assertEquals(List.of(2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500), range2);
    }
}