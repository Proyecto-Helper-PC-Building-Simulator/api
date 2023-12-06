package es.bit.api.rest.controller.basictables;

import es.bit.api.rest.dto.basictables.CaseFanSizeDTO;
import es.bit.api.rest.service.basictables.CaseFanSizeService;
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
@RequestMapping("/case_fan_sizes")
@Tag(name = "Case Fan Sizes Controller", description = "Related operations with case fan sizes")
public class CaseFanSizesController {
    @Autowired
    CaseFanSizeService caseFanSizeService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of case fan sizes")
    public Long count() {
        return this.caseFanSizeService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all case fan sizes paged")
    @ApiResponse(responseCode = "200", description = "Case fan sizes obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CaseFanSizeDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        List<CaseFanSizeDTO> content = this.caseFanSizeService.findAll(page, size);
        long totalElements = this.caseFanSizeService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a case fan size by ID")
    @ApiResponse(responseCode = "200", description = "Case fan size found.")
    @ApiResponse(responseCode = "404", description = "Case fan size not found.")
    public CaseFanSizeDTO findById(@PathVariable int id) {
        CaseFanSizeDTO caseFanSize = this.caseFanSizeService.findById(id);

        if (caseFanSize == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return caseFanSize;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new case fan size")
    @ApiResponse(responseCode = "201", description = "Case fan size created.")
    @ApiResponse(responseCode = "500", description = "Case fan size name is duplicated.")
    public CaseFanSizeDTO create(@RequestBody CaseFanSizeDTO caseFanSize) {
        return this.caseFanSizeService.create(caseFanSize);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a case fan size by ID")
    @ApiResponse(responseCode = "204", description = "Case fan size updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Case fan size name is duplicated.")
    public void updateCaseFanSize(@PathVariable int id, @RequestBody CaseFanSizeDTO caseFanSize) {
        if (id != caseFanSize.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.caseFanSizeService.update(caseFanSize);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a case fan size by ID")
    @ApiResponse(responseCode = "204", description = "Case fan size deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Case fan size cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody CaseFanSizeDTO caseFanSize) {
        if (id != caseFanSize.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.caseFanSizeService.delete(caseFanSize);
    }
}
