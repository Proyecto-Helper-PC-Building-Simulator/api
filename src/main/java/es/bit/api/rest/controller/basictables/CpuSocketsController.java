package es.bit.api.rest.controller.basictables;

import es.bit.api.rest.dto.basictables.CpuSocketDTO;
import es.bit.api.rest.service.basictables.CpuSocketService;
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
@RequestMapping("/cpu_sockets")
@Tag(name = "Cpu Sockets Controller", description = "Related operations with cpu sockets")
public class CpuSocketsController {
    @Autowired
    CpuSocketService cpuSocketService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of cpu sockets")
    public Long count() {
        return this.cpuSocketService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all cpu sockets paged")
    @ApiResponse(responseCode = "200", description = "Cpu sockets obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CpuSocketDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "false") Boolean withCpuCoolers
    ) {
        List<CpuSocketDTO> content = this.cpuSocketService.findAll(page, size, withCpuCoolers);
        long totalElements = this.cpuSocketService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a cpu socket by ID")
    @ApiResponse(responseCode = "200", description = "Cpu socket found.")
    @ApiResponse(responseCode = "404", description = "Cpu socket not found.")
    public CpuSocketDTO findById(
            @PathVariable int id,
            @RequestParam(required = false, defaultValue = "false") Boolean withCpuCoolers
    ) {
        CpuSocketDTO cpuSocket = this.cpuSocketService.findById(id, withCpuCoolers);

        if (cpuSocket == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return cpuSocket;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new cpu socket")
    @ApiResponse(responseCode = "201", description = "Cpu socket created.")
    @ApiResponse(responseCode = "500", description = "Cpu socket name is duplicated.")
    public CpuSocketDTO create(@RequestBody CpuSocketDTO cpuSocket) {
        return this.cpuSocketService.create(cpuSocket, true);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a cpu socket by ID")
    @ApiResponse(responseCode = "204", description = "Cpu socket updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Cpu socket name is duplicated.")
    public void updateCpuSocket(@PathVariable int id, @RequestBody CpuSocketDTO cpuSocket) {
        if (id != cpuSocket.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.cpuSocketService.update(cpuSocket, true);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a cpu socket by ID")
    @ApiResponse(responseCode = "204", description = "Cpu socket deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Cpu socket cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody CpuSocketDTO cpuSocket) {
        if (id != cpuSocket.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.cpuSocketService.delete(cpuSocket, true);
    }
}
