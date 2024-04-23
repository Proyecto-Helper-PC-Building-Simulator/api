package es.bit.api.rest.controller.componenttables;

import es.bit.api.persistence.model.componenttables.Component;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.componenttables.ComponentDTO;
import es.bit.api.rest.service.componenttables.ComponentService;
import es.bit.api.utils.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    @Operation(summary = "Get the total number of components")
    public Long count() {
        return super.count();
    }

    @Override
    @Operation(summary = "Get all components paged")
    @ApiResponse(responseCode = "200", description = "Components obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<ComponentDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "") Map<String, String> filters
    ) {
        return super.findAll(page, size, sortBy, sortDir, filters);
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

    @Operation(summary = "Get a component by ID")
    @ApiResponse(responseCode = "200", description = "Component found.")
    @ApiResponse(responseCode = "404", description = "Component not found.")
    public ComponentDTO findById(@PathVariable int id) {
        return super.findById(id);
    }

    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new component")
    @ApiResponse(responseCode = "201", description = "Component created.")
    @ApiResponse(responseCode = "500", description = "Component name is duplicated.")
    public ComponentDTO create(@RequestBody ComponentDTO component) {
        return super.create(component);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a component by ID")
    @ApiResponse(responseCode = "204", description = "Component updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Component name is duplicated.")
    public void update(@PathVariable int id, @RequestBody ComponentDTO component) {
        if (id != component.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        super.update(id, component);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a component by ID")
    @ApiResponse(responseCode = "204", description = "Component deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Component cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody ComponentDTO component) {
        if (id != component.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        super.delete(id);
    }
}
