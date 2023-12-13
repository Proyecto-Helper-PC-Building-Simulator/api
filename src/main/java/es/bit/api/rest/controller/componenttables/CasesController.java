package es.bit.api.rest.controller.componenttables;

import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
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

import java.util.List;

@RestController
@RequestMapping("/cases")
@Tag(name = "Cases Controller", description = "Related operations with cases")
public class CasesController {
    private final CaseService caseService;
    private final ComponentTypeService componentTypeService;

    @Autowired
    public CasesController(CaseService caseService, ComponentTypeService componentTypeService) {
        this.caseService = caseService;
        this.componentTypeService = componentTypeService;
    }


    @GetMapping("/count")
    @Operation(summary = "Get the total number of cases")
    public Long count() {
        return this.caseService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all cases paged")
    @ApiResponse(responseCode = "200", description = "Cases obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CaseDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        List<CaseDTO> content = this.caseService.findAll(page, size);
        long totalElements = this.caseService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a case by ID")
    @ApiResponse(responseCode = "200", description = "Case found.")
    @ApiResponse(responseCode = "404", description = "Case not found.")
    public CaseDTO findById(@PathVariable int id) {
        CaseDTO caseObject = this.caseService.findById(id);

        if (caseObject == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return caseObject;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new case")
    @ApiResponse(responseCode = "201", description = "Case created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Case name is duplicated.")
    public CaseDTO create(@RequestBody CaseDTO caseObject) {
        validateComponentType(caseObject);

        return this.caseService.create(caseObject);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a case by ID")
    @ApiResponse(responseCode = "204", description = "Case updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Case name is duplicated.")
    public void updateCase(@PathVariable int id, @RequestBody CaseDTO caseObject) {
        if (id != caseObject.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(caseObject);

        this.caseService.update(caseObject);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a case by ID")
    @ApiResponse(responseCode = "204", description = "Case deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Case cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody CaseDTO caseObject) {
        if (id != caseObject.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.caseService.delete(caseObject);
    }

    private void validateComponentType(CaseDTO caseObject) {
        ComponentTypeDTO componentType = componentTypeService.findById(caseObject.getComponentTypeDTO().getId());

        if (!"/cases".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
