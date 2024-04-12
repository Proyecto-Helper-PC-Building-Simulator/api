package es.bit.api.rest.controller.componenttables;

import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.componenttables.CableDTO;
import es.bit.api.rest.service.basictables.ComponentTypeService;
import es.bit.api.rest.service.componenttables.CableService;
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
@RequestMapping("/cables")
@Tag(name = "Cables Controller", description = "Related operations with cables")
public class CablesController {
    private final CableService cableService;
    private final ComponentTypeService componentTypeService;

    @Autowired
    public CablesController(CableService cableService, ComponentTypeService componentTypeService) {
        this.cableService = cableService;
        this.componentTypeService = componentTypeService;
    }


    @GetMapping("/count")
    @Operation(summary = "Get the total number of cables")
    public Long count() {
        return this.cableService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all cables paged")
    @ApiResponse(responseCode = "200", description = "Cables obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CableDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        List<CableDTO> content = this.cableService.findAll(page, size, sortBy, sortDir, filters);
        long totalElements = this.cableService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a cable by ID")
    @ApiResponse(responseCode = "200", description = "Cable found.")
    @ApiResponse(responseCode = "404", description = "Cable not found.")
    public CableDTO findById(
            @PathVariable int id
    ) {
        CableDTO cable = this.cableService.findById(id);

        if (cable == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return cable;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new cable")
    @ApiResponse(responseCode = "201", description = "Cable created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Cable name is duplicated.")
    public CableDTO create(
            @RequestBody CableDTO cable
    ) {
        validateComponentType(cable);

        return this.cableService.create(cable);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a cable by ID")
    @ApiResponse(responseCode = "204", description = "Cable updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Cable name is duplicated.")
    public void updateCable(
            @PathVariable int id,
            @RequestBody CableDTO cable
    ) {
        if (id != cable.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(cable);

        this.cableService.update(cable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a cable by ID")
    @ApiResponse(responseCode = "204", description = "Cable deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Cable cannot be deleted due to foreign keys.")
    public void delete(
            @PathVariable int id,
            @RequestBody CableDTO cable
    ) {
        if (id != cable.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.cableService.delete(cable);
    }

    private void validateComponentType(CableDTO cable) {
        ComponentTypeDTO componentType = componentTypeService.findById(cable.getComponentTypeDTO().getId());

        if (!"/cables".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
