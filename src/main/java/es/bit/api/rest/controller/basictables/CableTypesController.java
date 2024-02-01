package es.bit.api.rest.controller.basictables;

import es.bit.api.rest.dto.basictables.CableTypeDTO;
import es.bit.api.rest.service.basictables.CableTypeService;
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
@RequestMapping("/cable_types")
@Tag(name = "Cable Types Controller", description = "Related operations with cable types")
public class CableTypesController {
    @Autowired
    CableTypeService cableTypeService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of cable types")
    public Long count() {
        return this.cableTypeService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all cable types paged")
    @ApiResponse(responseCode = "200", description = "Cable types obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CableTypeDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        List<CableTypeDTO> content = this.cableTypeService.findAll(page, size);
        long totalElements = this.cableTypeService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a cable type by ID")
    @ApiResponse(responseCode = "200", description = "Cable type found.")
    @ApiResponse(responseCode = "404", description = "Cable type not found.")
    public CableTypeDTO findById(@PathVariable int id) {
        CableTypeDTO cableType = this.cableTypeService.findById(id);

        if (cableType == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return cableType;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new cable type")
    @ApiResponse(responseCode = "201", description = "Cable type created.")
    @ApiResponse(responseCode = "500", description = "Cable type name is duplicated.")
    public CableTypeDTO create(@RequestBody CableTypeDTO cableType) {
        return this.cableTypeService.create(cableType);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a cable type by ID")
    @ApiResponse(responseCode = "204", description = "Cable type updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Cable type name is duplicated.")
    public void updateCableType(@PathVariable int id, @RequestBody CableTypeDTO cableType) {
        if (id != cableType.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.cableTypeService.update(cableType);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a cable type by ID")
    @ApiResponse(responseCode = "204", description = "Cable type deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Cable type cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody CableTypeDTO cableType) {
        if (id != cableType.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.cableTypeService.delete(cableType);
    }
}
