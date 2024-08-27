package es.bit.api.rest.controller.componenttables;

import es.bit.api.persistence.model.componenttables.Case;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.dto.componenttables.CaseDTO;
import es.bit.api.rest.service.basictables.ComponentTypeService;
import es.bit.api.rest.service.componenttables.CaseService;
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
@RequestMapping("/cases")
@Tag(name = "Cases Controller", description = "Related operations with cases")
public class CasesController extends GenericController<CaseDTO, Case, Integer> {
    private final ComponentTypeService componentTypeService;


    @Autowired
    public CasesController(CaseService caseService, ComponentTypeService componentTypeService) {
        super(caseService);
        this.componentTypeService = componentTypeService;
    }


    @Override
    @Operation(summary = "Get the total number of cases")
    public Long count() {
        return super.count();
    }

    @Override
    @Operation(summary = "Get all cases paged")
    @ApiResponse(responseCode = "200", description = "Cases obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CaseDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        return super.findAll(page, size, sortBy, sortDir, filters);
    }

    @Operation(summary = "Get a case by ID")
    @ApiResponse(responseCode = "200", description = "Case found.")
    @ApiResponse(responseCode = "404", description = "Case not found.")
    public CaseDTO findById(@PathVariable int id) {
        return super.findById(id);
    }

    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new case")
    @ApiResponse(responseCode = "201", description = "Case created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Case name is duplicated.")
    public CaseDTO create(@RequestBody CaseDTO caseObject) {
        validateComponentType(caseObject);

        return super.create(caseObject);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a case by ID")
    @ApiResponse(responseCode = "204", description = "Case updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Case name is duplicated.")
    public void update(@PathVariable int id, @RequestBody CaseDTO caseObject) {
        if (id != caseObject.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(caseObject);

        super.update(id, caseObject);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a case by ID")
    @ApiResponse(responseCode = "204", description = "Case deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Case cannot be deleted due to foreign keys.")
    public void delete(
            @PathVariable int id,
            @RequestBody CaseDTO caseObject
    ) {
        if (id != caseObject.getComponentId()) {
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
    protected void validateComponentType(CaseDTO caseObject) {
        ComponentTypeDTO componentType = componentTypeService.findById(caseObject.getComponentTypeDTO().getId());

        if (!"/cases".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
