package es.bit.api.rest.controller.basictables;

import es.bit.api.rest.dto.basictables.MotherboardFormFactorDTO;
import es.bit.api.rest.service.basictables.MotherboardFormFactorService;
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
@RequestMapping("/motherboard_form_factors")
@Tag(name = "Motherboard Form Factor Controller", description = "Related operations with motherboard form factors")
public class MotherboardFormFactorsController {
    @Autowired
    MotherboardFormFactorService motherboardFormFactorService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of motherboard form factors")
    public Long count() {
        return this.motherboardFormFactorService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all motherboard form factors paged")
    @ApiResponse(responseCode = "200", description = "Motherboard form factors obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<MotherboardFormFactorDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(required = false, defaultValue = "false") Boolean withCases
    ) {
        List<MotherboardFormFactorDTO> content = this.motherboardFormFactorService.findAll(page, size, withCases);
        long totalElements = this.motherboardFormFactorService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a motherboard form factor by ID")
    @ApiResponse(responseCode = "200", description = "Motherboard form factor found.")
    @ApiResponse(responseCode = "404", description = "Motherboard form factor not found.")
    public MotherboardFormFactorDTO findById(
            @PathVariable int id,
            @RequestParam(required = false, defaultValue = "false") Boolean withCases
    ) {
        MotherboardFormFactorDTO motherboardFormFactor = this.motherboardFormFactorService.findById(id, withCases);

        if (motherboardFormFactor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return motherboardFormFactor;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new motherboard form factor")
    @ApiResponse(responseCode = "201", description = "Motherboard form factor created.")
    @ApiResponse(responseCode = "500", description = "Motherboard form factor name is duplicated.")
    public MotherboardFormFactorDTO create(
            @RequestBody MotherboardFormFactorDTO motherboardFormFactor
    ) {
        return this.motherboardFormFactorService.create(motherboardFormFactor, true);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a motherboard form factor by ID")
    @ApiResponse(responseCode = "204", description = "Motherboard form factor updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Motherboard form factor name is duplicated.")
    public void updateMotherboardFormFactor(
            @PathVariable int id,
            @RequestBody MotherboardFormFactorDTO motherboardFormFactor
    ) {
        if (id != motherboardFormFactor.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.motherboardFormFactorService.update(motherboardFormFactor, true);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a motherboard form factor by ID")
    @ApiResponse(responseCode = "204", description = "Motherboard form factor deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Motherboard form factor cannot be deleted due to foreign keys.")
    public void delete(
            @PathVariable int id,
            @RequestBody MotherboardFormFactorDTO motherboardFormFactor
    ) {
        if (id != motherboardFormFactor.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.motherboardFormFactorService.delete(motherboardFormFactor, true);
    }
}
