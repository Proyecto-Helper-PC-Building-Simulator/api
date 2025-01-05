package es.bit.api.rest.controller.components;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.components.ComponentDTO;
import es.bit.api.rest.service.components.ComponentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/components")
@Tag(name = "Components Controller", description = "Related operations with component s")
public class ComponentsController extends GenericController<ComponentDTO, Component, Integer> {
    private final ComponentService componentService;


    @Autowired
    public ComponentsController(ComponentService componentService) {
        super(componentService);
        this.componentService = componentService;
    }


    @Override
    @Operation(summary = "Get all components paged")
    @ApiResponse(responseCode = "200", description = "Components obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public Page<ComponentDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "") Map<String, String> filters
    ) {
        return super.findAll("components", page, size, sortBy, sortDir, filters);
    }

    @GetMapping("/multiple")
    @Operation(summary = "Get multiple components by IDs")
    @ApiResponse(responseCode = "200", description = "Components found.")
    @ApiResponse(responseCode = "404", description = "Component not found.")
    public List<ComponentDTO> findMultipleComponentsByIds(@RequestParam List<Integer> ids) {
        List<ComponentDTO> components = componentService.findComponentsByIds(ids);

        if (components.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No components found for the provided IDs.");
        }

        return components;
    }
}
