package es.bit.api.rest.controller.basictables;

import es.bit.api.rest.dto.basictables.CaseSizeDTO;
import es.bit.api.rest.service.basictables.CaseSizeService;
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
@RequestMapping("/case_sizes")
@Tag(name = "Case Sizes Controller", description = "Related operations with case sizes")
public class CaseSizesController {
    @Autowired
    CaseSizeService caseSizeService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of case sizes")
    public Long count() {
        return this.caseSizeService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all case sizes paged")
    @ApiResponse(responseCode = "200", description = "Case sizes obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CaseSizeDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        List<CaseSizeDTO> content = this.caseSizeService.findAll(page, size);
        long totalElements = this.caseSizeService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a case size by ID")
    @ApiResponse(responseCode = "200", description = "Case size found.")
    @ApiResponse(responseCode = "404", description = "Case size not found.")
    public CaseSizeDTO findById(@PathVariable int id) {
        CaseSizeDTO caseSize = this.caseSizeService.findById(id);

        if (caseSize == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return caseSize;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new case size")
    @ApiResponse(responseCode = "201", description = "Case size created.")
    @ApiResponse(responseCode = "500", description = "Case size name is duplicated.")
    public CaseSizeDTO create(@RequestBody CaseSizeDTO caseSize) {
        return this.caseSizeService.create(caseSize);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a case size by ID")
    @ApiResponse(responseCode = "204", description = "Case size updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Case size name is duplicated.")
    public void updateCaseSize(@PathVariable int id, @RequestBody CaseSizeDTO caseSize) {
        if (id != caseSize.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.caseSizeService.update(caseSize);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a case size by ID")
    @ApiResponse(responseCode = "204", description = "Case size deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Case size cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody CaseSizeDTO caseSize) {
        if (id != caseSize.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.caseSizeService.delete(caseSize);
    }
}
