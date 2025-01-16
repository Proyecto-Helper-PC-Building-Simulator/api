package es.bit.api.rest.controller;

import es.bit.api.config.SecurityConfig;
import es.bit.api.rest.service.FilterService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilterController.class)
@Import(SecurityConfig.class)
class FilterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilterService filterService;


    @Test
    void getFiltersByComponentType_validType_shouldReturnFilters() throws Exception {
        String componentType = "cpu";
        Map<String, Object> filters = Map.of(
                "manufacturers", List.of("Intel", "AMD"),
                "priceRange", Map.of("minPrice", 100.0, "maxPrice", 1200.0)
        );

        Mockito.when(filterService.getFiltersForComponentType(componentType)).thenReturn(filters);

        mockMvc.perform(get("/filters/{componentType}", componentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.manufacturers").isArray())
                .andExpect(jsonPath("$.manufacturers[0]").value("Intel"))
                .andExpect(jsonPath("$.priceRange.minPrice").value(100.0))
                .andExpect(jsonPath("$.priceRange.maxPrice").value(1200.0));
    }

    @Test
    void getFiltersByComponentType_invalidType_shouldReturnBadRequest() throws Exception {
        String componentType = "invalidType";

        Mockito.when(filterService.getFiltersForComponentType(componentType))
                .thenThrow(new IllegalArgumentException("Invalid component type: " + componentType));

        mockMvc.perform(get("/filters/{componentType}", componentType))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid component type: " + componentType));
    }
}