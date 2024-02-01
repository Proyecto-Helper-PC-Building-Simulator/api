package es.bit.api.rest.controller.basictables;

import es.bit.api.rest.dto.basictables.LightingDTO;
import es.bit.api.rest.service.basictables.LightingService;
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
@RequestMapping("/lightings")
@Tag(name = "Lightings Controller", description = "Related operations with lightings")
public class LightingsController {
    @Autowired
    LightingService lightingService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of lightings")
    public Long count() {
        return this.lightingService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all lightings paged")
    @ApiResponse(responseCode = "200", description = "Lightings obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<LightingDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        List<LightingDTO> content = this.lightingService.findAll(page, size);
        long totalElements = this.lightingService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a lighting by ID")
    @ApiResponse(responseCode = "200", description = "Lighting found.")
    @ApiResponse(responseCode = "404", description = "Lighting not found.")
    public LightingDTO findById(@PathVariable int id) {
        LightingDTO lighting = this.lightingService.findById(id);

        if (lighting == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return lighting;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new lighting")
    @ApiResponse(responseCode = "201", description = "Lighting created.")
    @ApiResponse(responseCode = "500", description = "Lighting name is duplicated.")
    public LightingDTO create(@RequestBody LightingDTO lighting) {
        return this.lightingService.create(lighting);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a lighting by ID")
    @ApiResponse(responseCode = "204", description = "Lighting updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Lighting name is duplicated.")
    public void updateLighting(@PathVariable int id, @RequestBody LightingDTO lighting) {
        if (id != lighting.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.lightingService.update(lighting);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a lighting by ID")
    @ApiResponse(responseCode = "204", description = "Lighting deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Lighting cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody LightingDTO lighting) {
        if (id != lighting.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.lightingService.delete(lighting);
    }
}
