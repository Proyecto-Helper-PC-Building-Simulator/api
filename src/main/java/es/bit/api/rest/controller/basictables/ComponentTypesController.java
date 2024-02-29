package es.bit.api.rest.controller.basictables;

import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.service.basictables.ComponentTypeService;
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
@RequestMapping("/component_types")
@Tag(name = "Component Types Controller", description = "Related operations with component types")
public class ComponentTypesController {
    @Autowired
    ComponentTypeService componentTypeService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of component types")
    public Long count() {
        return this.componentTypeService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all component types paged")
    @ApiResponse(responseCode = "200", description = "Component types obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<ComponentTypeDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        List<ComponentTypeDTO> content = this.componentTypeService.findAll(page, size);
        long totalElements = this.componentTypeService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a component type by ID")
    @ApiResponse(responseCode = "200", description = "Component type found.")
    @ApiResponse(responseCode = "404", description = "Component type not found.")
    public ComponentTypeDTO findById(@PathVariable int id) {
        ComponentTypeDTO componentType = this.componentTypeService.findById(id);

        if (componentType == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return componentType;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new component type")
    @ApiResponse(responseCode = "201", description = "Component type created.")
    @ApiResponse(responseCode = "500", description = "Component type name is duplicated.")
    public ComponentTypeDTO create(@RequestBody ComponentTypeDTO componentType) {
        return this.componentTypeService.create(componentType);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a component type by ID")
    @ApiResponse(responseCode = "204", description = "Component type updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Component type name is duplicated.")
    public void updateComponentType(@PathVariable int id, @RequestBody ComponentTypeDTO componentType) {
        if (id != componentType.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.componentTypeService.update(componentType);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a component type by ID")
    @ApiResponse(responseCode = "204", description = "Component type deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Component type cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody ComponentTypeDTO componentType) {
        if (id != componentType.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.componentTypeService.delete(componentType);
    }
}
