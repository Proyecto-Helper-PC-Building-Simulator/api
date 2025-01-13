package es.bit.api.rest.controller;

import es.bit.api.exceptions.ResourceNotFoundException;
import es.bit.api.persistence.model.components.Component;
import es.bit.api.rest.dto.components.ComponentDTO;
import es.bit.api.rest.service.GenericService;
import es.bit.api.utils.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

public abstract class GenericController<D extends ComponentDTO, C extends Component, I extends Serializable> {
    protected final GenericService<D, C, I> genericService;

    @Autowired
    protected GenericController(GenericService<D, C, I> genericService) {
        this.genericService = genericService;
    }


    @Operation(summary = "Get all entities paged")
    @ApiResponse(responseCode = "200", description = "Entities obtained correctly.")
    @ApiResponse(responseCode = "204", description = "Entities not found")
    @ApiResponse(responseCode = "404", description = "Error getting the selected page.")
    public ResponseEntity<?> findAll(
            String componentType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PagedResponse<?> pageRequest = this.genericService.findAll(componentType, PageRequest.of(page, size, sort), filters);

        if (!pageRequest.isHasContent() && pageRequest.getTotalPages() == 0) {
            throw new ResourceNotFoundException("No results for the actual filters");
        } else if (page >= pageRequest.getTotalPages()) {
            throw new ResourceNotFoundException("The page does not exist");
        }

        return ResponseEntity.ok(pageRequest);
    }

    @GetMapping("")
    @Operation(summary = "Get all cables paged")
    @ApiResponse(responseCode = "200", description = "Cables obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public abstract ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    );

    @GetMapping("/{id}")
    @Operation(summary = "Get an entity by ID")
    @ApiResponse(responseCode = "200", description = "Entity found.")
    @ApiResponse(responseCode = "404", description = "Entity not found.")
    public ResponseEntity<D> findById(@PathVariable I id) {
        return this.genericService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("The object with the ID %s does not exist".formatted(id)));
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new entity")
    @ApiResponse(responseCode = "201", description = "Entity created.")
    public ResponseEntity<D> create(@RequestBody D dto) {
        return ResponseEntity.ok(this.genericService.save(dto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update an entity by ID")
    @ApiResponse(responseCode = "204", description = "Entity updated correctly.")
    public ResponseEntity<D> update(@PathVariable I id, @RequestBody D dto) {
        try {
            D updatedDto = this.genericService.update(id, dto);

            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete an entity by ID")
    @ApiResponse(responseCode = "204", description = "Entity deleted correctly.")
    public ResponseEntity<Void> delete(@PathVariable I id) {
        if (genericService.findById(id).isPresent()) {
            genericService.delete(id);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
