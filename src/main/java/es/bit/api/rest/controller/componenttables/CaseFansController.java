package es.bit.api.rest.controller.componenttables;

import es.bit.api.rest.dto.componenttables.CaseFanDTO;
import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.service.componenttables.CaseFanService;
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
import java.util.Map;

@RestController
@RequestMapping("/case_fans")
@Tag(name = "Case Fans Controller", description = "Related operations with case fans")
public class CaseFansController {
    private final CaseFanService caseFanService;
    private final ComponentTypeService componentTypeService;

    @Autowired
    public CaseFansController(CaseFanService caseFanService, ComponentTypeService componentTypeService) {
        this.caseFanService = caseFanService;
        this.componentTypeService = componentTypeService;
    }


    @GetMapping("/count")
    @Operation(summary = "Get the total number of case fans")
    public Long count() {
        return this.caseFanService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all case fans paged")
    @ApiResponse(responseCode = "200", description = "CaseFans obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CaseFanDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        List<CaseFanDTO> content = this.caseFanService.findAll(page, size, sortBy, sortDir, filters);
        long totalElements = this.caseFanService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a case fan by ID")
    @ApiResponse(responseCode = "200", description = "Case fan found.")
    @ApiResponse(responseCode = "404", description = "Case fan not found.")
    public CaseFanDTO findById(@PathVariable int id) {
        CaseFanDTO caseFan = this.caseFanService.findById(id);

        if (caseFan == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return caseFan;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new case fan")
    @ApiResponse(responseCode = "201", description = "Case fan created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Case fan name is duplicated.")
    public CaseFanDTO create(@RequestBody CaseFanDTO caseFan) {
        validateComponentType(caseFan);

        return this.caseFanService.create(caseFan);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a case fan by ID")
    @ApiResponse(responseCode = "204", description = "Case fan updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Case fan name is duplicated.")
    public void updateCaseFan(@PathVariable int id, @RequestBody CaseFanDTO caseFan) {
        if (id != caseFan.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(caseFan);

        this.caseFanService.update(caseFan);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a case fan by ID")
    @ApiResponse(responseCode = "204", description = "Case fan deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Case fan cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody CaseFanDTO caseFan) {
        if (id != caseFan.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.caseFanService.delete(caseFan);
    }

    private void validateComponentType(CaseFanDTO caseFan) {
        ComponentTypeDTO componentType = componentTypeService.findById(caseFan.getComponentTypeDTO().getId());

        if (!"/case_fans".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
