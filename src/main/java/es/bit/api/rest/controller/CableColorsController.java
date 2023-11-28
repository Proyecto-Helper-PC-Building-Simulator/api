package es.bit.api.rest.controller;

import es.bit.api.rest.dto.CableColorDTO;
import es.bit.api.rest.service.CableColorService;
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
@RequestMapping("/cable_colors")
@Tag(name = "Cable Colors Controller", description = "Related operations with cable colors")
public class CableColorsController {
    @Autowired
    CableColorService cableColorService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of cable colors")
    public Long count() {
        return this.cableColorService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all wire colors paged")
    @ApiResponse(responseCode = "200", description = "Cable colors obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CableColorDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        List<CableColorDTO> content = this.cableColorService.findAll(page, size);
        long totalElements = this.cableColorService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a cable color by ID")
    @ApiResponse(responseCode = "200", description = "Cable color found.")
    @ApiResponse(responseCode = "404", description = "Cable color not found.")
    public CableColorDTO findById(@PathVariable int id) {
        CableColorDTO cableColor = this.cableColorService.findById(id);

        if (cableColor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return cableColor;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new cable color")
    @ApiResponse(responseCode = "201", description = "Cable color created.")
    @ApiResponse(responseCode = "500", description = "Cable color name is duplicated.")
    public CableColorDTO create(@RequestBody CableColorDTO cableColor) {
        return this.cableColorService.create(cableColor);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a cable color by ID")
    @ApiResponse(responseCode = "204", description = "Cable color updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Cable color name is duplicated.")
    public void updateCableColor(@PathVariable int id, @RequestBody CableColorDTO cableColor) {
        if (id != cableColor.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.cableColorService.update(cableColor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a cable color by ID")
    @ApiResponse(responseCode = "204", description = "Cable color deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    public void delete(@PathVariable int id, @RequestBody CableColorDTO cableColor) {
        if (id != cableColor.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.cableColorService.delete(cableColor);
    }
}
