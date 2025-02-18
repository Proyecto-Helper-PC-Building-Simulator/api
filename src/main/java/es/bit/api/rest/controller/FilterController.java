package es.bit.api.rest.controller;

import es.bit.api.rest.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/filters")
public class FilterController {
    @Autowired
    private FilterService filterService;


    @GetMapping("/componentTypes")
    public ResponseEntity<?> getAllComponentTypes() {
        List<?> componentTypes = filterService.findAllComponentTypes();

        return ResponseEntity.ok(componentTypes);
    }

    @GetMapping("/{componentType}")
    public ResponseEntity<?> getFiltersByComponentType(@PathVariable String componentType) {
        try {
            Map<String, Object> filters = filterService.getFiltersForComponentType(componentType);
            return ResponseEntity.ok(filters);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Unexpected error occurred"));
        }
    }
}
