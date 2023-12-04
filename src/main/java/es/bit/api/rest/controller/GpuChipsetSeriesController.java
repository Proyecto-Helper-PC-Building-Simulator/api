package es.bit.api.rest.controller;

import es.bit.api.rest.dto.GpuChipsetSerieDTO;
import es.bit.api.rest.service.GpuChipsetSerieService;
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
@RequestMapping("/gpu_chipset_series")
@Tag(name = "Gpu Chipset Series Controller", description = "Related operations with gpu chipset series")
public class GpuChipsetSeriesController {
    @Autowired
    GpuChipsetSerieService gpuChipsetSerieService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of gpu chipset series")
    public Long count() {
        return this.gpuChipsetSerieService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all gpu chipset series paged")
    @ApiResponse(responseCode = "200", description = "Case sizes obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<GpuChipsetSerieDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        List<GpuChipsetSerieDTO> content = this.gpuChipsetSerieService.findAll(page, size);
        long totalElements = this.gpuChipsetSerieService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a gpu chipset serie by ID")
    @ApiResponse(responseCode = "200", description = "Case size found.")
    @ApiResponse(responseCode = "404", description = "Case size not found.")
    public GpuChipsetSerieDTO findById(@PathVariable int id) {
        GpuChipsetSerieDTO gpuChipsetSerie = this.gpuChipsetSerieService.findById(id);

        if (gpuChipsetSerie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return gpuChipsetSerie;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new gpu chipset serie")
    @ApiResponse(responseCode = "201", description = "Case size created.")
    @ApiResponse(responseCode = "500", description = "Case size name is duplicated.")
    public GpuChipsetSerieDTO create(@RequestBody GpuChipsetSerieDTO gpuChipsetSerie) {
        return this.gpuChipsetSerieService.create(gpuChipsetSerie);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a gpu chipset serie by ID")
    @ApiResponse(responseCode = "204", description = "Case size updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Case size name is duplicated.")
    public void updateGpuChipsetSerie(@PathVariable int id, @RequestBody GpuChipsetSerieDTO gpuChipsetSerie) {
        if (id != gpuChipsetSerie.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.gpuChipsetSerieService.update(gpuChipsetSerie);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a gpu chipset serie by ID")
    @ApiResponse(responseCode = "204", description = "Case size deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Case size cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody GpuChipsetSerieDTO gpuChipsetSerie) {
        if (id != gpuChipsetSerie.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.gpuChipsetSerieService.delete(gpuChipsetSerie);
    }
}
