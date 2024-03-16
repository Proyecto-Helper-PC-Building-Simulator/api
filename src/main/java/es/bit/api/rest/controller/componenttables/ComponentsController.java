package es.bit.api.rest.controller.componenttables;

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

@RestController
@RequestMapping("/components")
@Tag(name = "Components Controller", description = "Related operations with component s")
public class ComponentsController {
    @Autowired
    ComponentService componentService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of components")
    public Long count() {
        return this.componentService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all components paged")
    @ApiResponse(responseCode = "200", description = "Components obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<ComponentDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String componentType,
            @RequestParam(required = false) String name
    ) {
        List<ComponentDTO> content;

        if (componentType != null && !componentType.isEmpty()) {
            switch (componentType.toLowerCase()) {
                case "cpucooler" -> componentType = "cpu cooler";
                case "powersupply" -> componentType = "power supply";
            }

            if (name != null && !name.isEmpty()) {
                content = this.componentService.findAllByComponentTypeAndName(componentType, name, page, size);
            } else {
                content = this.componentService.findAllByComponentType(componentType, page, size);
            }
        } else {
            content = this.componentService.findAll(page, size);
        }

        long totalElements = this.componentService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a component by ID")
    @ApiResponse(responseCode = "200", description = "Component found.")
    @ApiResponse(responseCode = "404", description = "Component not found.")
    public ComponentDTO findById(@PathVariable int id) {
        ComponentDTO component = this.componentService.findById(id);

        if (component == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return component;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new component")
    @ApiResponse(responseCode = "201", description = "Component created.")
    @ApiResponse(responseCode = "500", description = "Component name is duplicated.")
    public ComponentDTO create(@RequestBody ComponentDTO component) {
        return this.componentService.create(component);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a component by ID")
    @ApiResponse(responseCode = "204", description = "Component updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Component name is duplicated.")
    public void updateComponent(@PathVariable int id, @RequestBody ComponentDTO component) {
        if (id != component.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.componentService.update(component);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a component by ID")
    @ApiResponse(responseCode = "204", description = "Component deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Component cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody ComponentDTO component) {
        if (id != component.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.componentService.delete(component);
    }
}
