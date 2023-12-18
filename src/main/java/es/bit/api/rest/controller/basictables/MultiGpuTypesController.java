package es.bit.api.rest.controller.basictables;

import es.bit.api.rest.dto.basictables.MultiGpuTypeDTO;
import es.bit.api.rest.service.basictables.MultiGpuTypeService;
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
@RequestMapping("/multi_gpu_types")
@Tag(name = "Multi Gpu Type Controller", description = "Related operations with multi gpu types")
public class MultiGpuTypesController {
    @Autowired
    MultiGpuTypeService multiGpuTypeService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of multi gpu types")
    public Long count() {
        return this.multiGpuTypeService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all multi gpu types paged")
    @ApiResponse(responseCode = "200", description = "Multi gpu types obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<MultiGpuTypeDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "false") Boolean withMotherboards
    ) {
        List<MultiGpuTypeDTO> content = this.multiGpuTypeService.findAll(page, size, withMotherboards);
        long totalElements = this.multiGpuTypeService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a multi gpu type by ID")
    @ApiResponse(responseCode = "200", description = "Multi gpu type found.")
    @ApiResponse(responseCode = "404", description = "Multi gpu type not found.")
    public MultiGpuTypeDTO findById(
            @PathVariable int id,
            @RequestParam(required = false, defaultValue = "false") Boolean withMotherboards
    ) {
        MultiGpuTypeDTO multiGpuType = this.multiGpuTypeService.findById(id, withMotherboards);

        if (multiGpuType == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return multiGpuType;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new multi gpu type")
    @ApiResponse(responseCode = "201", description = "Multi gpu type created.")
    @ApiResponse(responseCode = "500", description = "Multi gpu type name is duplicated.")
    public MultiGpuTypeDTO create(@RequestBody MultiGpuTypeDTO multiGpuType) {
        return this.multiGpuTypeService.create(multiGpuType, true);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a multi gpu type by ID")
    @ApiResponse(responseCode = "204", description = "Multi gpu type updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Multi gpu type name is duplicated.")
    public void updateMultiGpuType(
            @PathVariable int id,
            @RequestBody MultiGpuTypeDTO multiGpuType
    ) {
        if (id != multiGpuType.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.multiGpuTypeService.update(multiGpuType, true);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a multi gpu type by ID")
    @ApiResponse(responseCode = "204", description = "Multi gpu type deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Multi gpu type cannot be deleted due to foreign keys.")
    public void delete(
            @PathVariable int id,
            @RequestBody MultiGpuTypeDTO multiGpuType
    ) {
        if (id != multiGpuType.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.multiGpuTypeService.delete(multiGpuType, false);
    }
}
