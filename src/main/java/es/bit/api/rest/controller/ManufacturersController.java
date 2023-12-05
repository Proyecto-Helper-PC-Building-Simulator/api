package es.bit.api.rest.controller;

import es.bit.api.rest.dto.ManufacturerDTO;
import es.bit.api.rest.service.ManufacturerService;
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
@RequestMapping("/manufacturers")
@Tag(name = "Manufacturers Controller", description = "Related operations with manufacturers")
public class ManufacturersController {
    @Autowired
    ManufacturerService manufacturerService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of manufacturers")
    public Long count() {
        return this.manufacturerService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all manufacturers paged")
    @ApiResponse(responseCode = "200", description = "Manufacturers obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<ManufacturerDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        List<ManufacturerDTO> content = this.manufacturerService.findAll(page, size);
        long totalElements = this.manufacturerService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a manufacturer by ID")
    @ApiResponse(responseCode = "200", description = "Manufacturer found.")
    @ApiResponse(responseCode = "404", description = "Manufacturer not found.")
    public ManufacturerDTO findById(@PathVariable int id) {
        ManufacturerDTO manufacturer = this.manufacturerService.findById(id);

        if (manufacturer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return manufacturer;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new manufacturer")
    @ApiResponse(responseCode = "201", description = "Manufacturer created.")
    @ApiResponse(responseCode = "500", description = "Manufacturer name is duplicated.")
    public ManufacturerDTO create(@RequestBody ManufacturerDTO manufacturer) {
        return this.manufacturerService.create(manufacturer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a manufacturer by ID")
    @ApiResponse(responseCode = "204", description = "Manufacturer updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Manufacturer name is duplicated.")
    public void updateManufacturer(@PathVariable int id, @RequestBody ManufacturerDTO manufacturer) {
        if (id != manufacturer.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.manufacturerService.update(manufacturer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a manufacturer by ID")
    @ApiResponse(responseCode = "204", description = "Manufacturer deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Manufacturer cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody ManufacturerDTO manufacturer) {
        if (id != manufacturer.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.manufacturerService.delete(manufacturer);
    }
}
