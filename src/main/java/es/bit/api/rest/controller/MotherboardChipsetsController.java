package es.bit.api.rest.controller;

import es.bit.api.rest.dto.MotherboardChipsetDTO;
import es.bit.api.rest.service.MotherboardChipsetService;
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
@RequestMapping("/motherboard_chipsets")
@Tag(name = "Motherboard Chipset Controller", description = "Related operations with motherboard chipsets")
public class MotherboardChipsetsController {
    @Autowired
    MotherboardChipsetService motherboardChipsetService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of motherboard chipsets")
    public Long count() {
        return this.motherboardChipsetService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all motherboard chipsets paged")
    @ApiResponse(responseCode = "200", description = "Motherboard chipsets obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<MotherboardChipsetDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        List<MotherboardChipsetDTO> content = this.motherboardChipsetService.findAll(page, size);
        long totalElements = this.motherboardChipsetService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a motherboard chipset by ID")
    @ApiResponse(responseCode = "200", description = "Motherboard chipset found.")
    @ApiResponse(responseCode = "404", description = "Motherboard chipset not found.")
    public MotherboardChipsetDTO findById(@PathVariable int id) {
        MotherboardChipsetDTO motherboardChipset = this.motherboardChipsetService.findById(id);

        if (motherboardChipset == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return motherboardChipset;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new motherboard chipset")
    @ApiResponse(responseCode = "201", description = "Motherboard chipset created.")
    @ApiResponse(responseCode = "500", description = "Motherboard chipset name is duplicated.")
    public MotherboardChipsetDTO create(@RequestBody MotherboardChipsetDTO motherboardChipset) {
        return this.motherboardChipsetService.create(motherboardChipset);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a motherboard chipset by ID")
    @ApiResponse(responseCode = "204", description = "Motherboard chipset updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Motherboard chipset name is duplicated.")
    public void updateMotherboardChipset(@PathVariable int id, @RequestBody MotherboardChipsetDTO motherboardChipset) {
        if (id != motherboardChipset.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.motherboardChipsetService.update(motherboardChipset);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a motherboard chipset by ID")
    @ApiResponse(responseCode = "204", description = "Motherboard chipset deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Motherboard chipset cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody MotherboardChipsetDTO motherboardChipset) {
        if (id != motherboardChipset.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.motherboardChipsetService.delete(motherboardChipset);
    }
}
