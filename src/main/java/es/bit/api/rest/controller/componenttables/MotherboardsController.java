package es.bit.api.rest.controller.componenttables;

import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.componenttables.MotherboardDTO;
import es.bit.api.rest.service.basictables.ComponentTypeService;
import es.bit.api.rest.service.componenttables.MotherboardService;
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
@RequestMapping("/motherboards")
@Tag(name = "Motherboards Controller", description = "Related operations with motherboards")
public class MotherboardsController {
    private final MotherboardService motherboardService;
    private final ComponentTypeService componentTypeService;

    @Autowired
    public MotherboardsController(MotherboardService motherboardService, ComponentTypeService componentTypeService) {
        this.motherboardService = motherboardService;
        this.componentTypeService = componentTypeService;
    }


    @GetMapping("/count")
    @Operation(summary = "Get the total number of motherboards")
    public Long count() {
        return this.motherboardService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all motherboards paged")
    @ApiResponse(responseCode = "200", description = "Motherboards obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<MotherboardDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        List<MotherboardDTO> content = this.motherboardService.findAll(page, size, sortBy, sortDir, filters);
        long totalElements = this.motherboardService.count();

        int totalPages = (int) Math.ceil((double) totalElements / size);
        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a motherboard by ID")
    @ApiResponse(responseCode = "200", description = "Motherboard found.")
    @ApiResponse(responseCode = "404", description = "Motherboard not found.")
    public MotherboardDTO findById(@PathVariable int id) {
        MotherboardDTO motherboard = this.motherboardService.findById(id);

        if (motherboard == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return motherboard;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new motherboard")
    @ApiResponse(responseCode = "201", description = "Motherboard created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Motherboard name is duplicated.")
    public MotherboardDTO create(
            @RequestBody MotherboardDTO motherboard
    ) {
        validateComponentType(motherboard);

        return this.motherboardService.create(motherboard);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a motherboard by ID")
    @ApiResponse(responseCode = "204", description = "Motherboard updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Motherboard name is duplicated.")
    public void updateMotherboard(
            @PathVariable int id,
            @RequestBody MotherboardDTO motherboard
    ) {
        if (id != motherboard.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(motherboard);

        this.motherboardService.update(motherboard);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a motherboard by ID")
    @ApiResponse(responseCode = "204", description = "Motherboard deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Motherboard cannot be deleted due to foreign keys.")
    public void delete(
            @PathVariable int id,
            @RequestBody MotherboardDTO motherboard
    ) {
        if (id != motherboard.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.motherboardService.delete(motherboard);
    }

    private void validateComponentType(MotherboardDTO motherboard) {
        ComponentTypeDTO componentType = componentTypeService.findById(motherboard.getComponentTypeDTO().getId());

        if (!"/motherboards".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
