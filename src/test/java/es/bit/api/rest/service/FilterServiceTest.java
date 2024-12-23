package es.bit.api.rest.service;

import es.bit.api.persistence.repository.jpa.components.IComponentJpaRepository;
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
    private IComponentJpaRepository componentRepository;

    @Mock
    private IComponentTypeJPARepository componentTypeRepository;

    @InjectMocks
    private FilterService filterService;


    @Test
    public void getFiltersForComponentType_validType_shouldReturnFilters() {
        String componentType = "cpu";
        List<String> manufacturers = List.of("Intel", "AMD");
        Map<String, Double> priceRange = Map.of("minPrice", 100.0, "maxPrice", 1200.0);

        Mockito.when(componentTypeRepository.existsByNameIdentifier(componentType)).thenReturn(true);
        Mockito.when(componentRepository.findDistinctManufacturersByType(componentType)).thenReturn(manufacturers);
        Mockito.when(componentRepository.findPriceRangeByType(componentType)).thenReturn(priceRange);

        Map<String, Object> filters = filterService.getFiltersForComponentType(componentType);

        Assertions.assertEquals(manufacturers, filters.get("manufacturers"));
        Assertions.assertEquals(priceRange, filters.get("priceRange"));
    }

    @Test
    void getFiltersForComponentType_invalidType_shouldThrowException() {
        String componentType = "invalidType";

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                filterService.getFiltersForComponentType(componentType)
        );

        Assertions.assertEquals("Invalid component type: " + componentType, exception.getMessage());
    }
}