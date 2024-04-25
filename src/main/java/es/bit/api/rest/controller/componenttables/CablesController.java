package es.bit.api.rest.controller.componenttables;

import es.bit.api.persistence.model.componenttables.Cable;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
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

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/cables")
@Tag(name = "Cables Controller", description = "Related operations with cables")
public class CablesController extends GenericController<CableDTO, Cable, Integer> {
    private final ComponentTypeService componentTypeService;


    @Autowired
    public CablesController(CableService cableService, ComponentTypeService componentTypeService) {
        super(cableService);
        this.componentTypeService = componentTypeService;
    }


    @Override
    @Operation(summary = "Get the total number of cables")
    public Long count() {
        return super.count();
    }

    @Override
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
        return super.findAll(page, size, sortBy, sortDir, filters);
    }

    @Operation(summary = "Get a cable by ID")
    @ApiResponse(responseCode = "200", description = "Cable found.")
    @ApiResponse(responseCode = "404", description = "Cable not found.")
    public CableDTO findById(@PathVariable int id) {
        return super.findById(id);
    }

    @Override
    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new cable")
    @ApiResponse(responseCode = "201", description = "Cable created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Cable name is duplicated.")
    public CableDTO create(@RequestBody CableDTO cable) {
        validateComponentType(cable);

        return super.create(cable);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a cable by ID")
    @ApiResponse(responseCode = "204", description = "Cable updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Cable name is duplicated.")
    public void update(@PathVariable int id, @RequestBody CableDTO cable) {
        if (id != cable.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(cable);

        super.update(id, cable);
    }

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

        super.delete(id);
    }

    @Override
    @Operation(summary = "Get the highest and lowest price of CPUs")
    public Map<String, Double> getPriceRange() {
        return super.getPriceRange();
    }

    @Override
    @Operation(summary = "Get a list of manufacturers without duplicates")
    public Set<ManufacturerDTO> getManufacturers() {
        return super.getManufacturers();
    }


    @Override
    protected void validateComponentType(CableDTO cable) {
        ComponentTypeDTO componentType = componentTypeService.findById(cable.getComponentTypeDTO().getId());

        if (!"/cables".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
