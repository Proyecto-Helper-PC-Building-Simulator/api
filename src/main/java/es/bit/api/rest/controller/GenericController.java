package es.bit.api.rest.controller;

import es.bit.api.rest.dto.basictables.LightingDTO;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.service.GenericService;
import es.bit.api.utils.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class GenericController<D, C, I> {
    protected final GenericService<D, C, I> genericService;

    @Autowired
    protected GenericController(GenericService<D, C, I> genericService) {
        this.genericService = genericService;
    }

    @GetMapping("/count")
    @Operation(summary = "Get the total number of entities")
    public Long count() {
        return this.genericService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all entities paged")
    @ApiResponse(responseCode = "200", description = "Entities obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<D> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        List<D> content = this.genericService.findAll(page, size, sortBy, sortDir, filters);
        long totalElements = this.genericService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an entity by ID")
    @ApiResponse(responseCode = "200", description = "Entity found.")
    @ApiResponse(responseCode = "404", description = "Entity not found.")
    public D findById(@PathVariable I id) {
        D entity = this.genericService.findById(id);

        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return entity;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new entity")
    @ApiResponse(responseCode = "201", description = "Entity created.")
    public D create(@RequestBody D dto) {
        return this.genericService.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update an entity by ID")
    @ApiResponse(responseCode = "204", description = "Entity updated correctly.")
    public void update(@PathVariable I id, @RequestBody D dto) {
        this.genericService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete an entity by ID")
    @ApiResponse(responseCode = "204", description = "Entity deleted correctly.")
    public void delete(@PathVariable I id) {
        D entity = this.genericService.findById(id);
        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }
        this.genericService.delete(entity);
    }

    @GetMapping("/price-range")
    @Operation(summary = "Get the highest and lowest price of CPUs")
    public Map<String, Double> getPriceRange() {
        return genericService.getPriceRange();
    }

    @GetMapping("/manufacturers")
    @Operation(summary = "Get a list of manufacturers without duplicates")
    public Set<ManufacturerDTO> getManufacturers() {
        return genericService.getManufacturers();
    }

    @GetMapping("/lightings")
    @Operation(summary = "Get a list of lightings without duplicates")
    public Set<LightingDTO> getLightings() {
        return genericService.getLightings();
    }


    protected void validateComponentType(D dto) {}
}
