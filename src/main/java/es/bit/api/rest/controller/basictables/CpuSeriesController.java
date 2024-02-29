package es.bit.api.rest.controller.basictables;

import es.bit.api.rest.dto.basictables.CpuSerieDTO;
import es.bit.api.rest.service.basictables.CpuSerieService;
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
@RequestMapping("/cpu_series")
@Tag(name = "Cpu Series Controller", description = "Related operations with cpu series")
public class CpuSeriesController {
    @Autowired
    CpuSerieService cpuSerieService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of cpu series")
    public Long count() {
        return this.cpuSerieService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all cpu series paged")
    @ApiResponse(responseCode = "200", description = "Cpu series obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CpuSerieDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        List<CpuSerieDTO> content = this.cpuSerieService.findAll(page, size);
        long totalElements = this.cpuSerieService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a cpu serie by ID")
    @ApiResponse(responseCode = "200", description = "Cpu serie found.")
    @ApiResponse(responseCode = "404", description = "Cpu serie not found.")
    public CpuSerieDTO findById(@PathVariable int id) {
        CpuSerieDTO cpuSerie = this.cpuSerieService.findById(id);

        if (cpuSerie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return cpuSerie;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new cpu serie")
    @ApiResponse(responseCode = "201", description = "Cpu serie created.")
    @ApiResponse(responseCode = "500", description = "Cpu serie name is duplicated.")
    public CpuSerieDTO create(@RequestBody CpuSerieDTO cpuSerie) {
        return this.cpuSerieService.create(cpuSerie);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a cpu serie by ID")
    @ApiResponse(responseCode = "204", description = "Cpu serie updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Cpu serie name is duplicated.")
    public void updateCpuSerie(@PathVariable int id, @RequestBody CpuSerieDTO cpuSerie) {
        if (id != cpuSerie.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.cpuSerieService.update(cpuSerie);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a cpu serie by ID")
    @ApiResponse(responseCode = "204", description = "Cpu serie deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Cpu serie cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody CpuSerieDTO cpuSerie) {
        if (id != cpuSerie.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.cpuSerieService.delete(cpuSerie);
    }
}
