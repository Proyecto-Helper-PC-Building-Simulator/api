package es.bit.api.rest.controller.componenttables;

import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.componenttables.CpuDTO;
import es.bit.api.rest.service.basictables.ComponentTypeService;
import es.bit.api.rest.service.componenttables.CpuService;
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
@RequestMapping("/cpus")
@Tag(name = "Cpus Controller", description = "Related operations with cpus")
public class CpusController {
    private final CpuService cpuService;
    private final ComponentTypeService componentTypeService;

    @Autowired
    public CpusController(CpuService cpuService, ComponentTypeService componentTypeService) {
        this.cpuService = cpuService;
        this.componentTypeService = componentTypeService;
    }


    @GetMapping("/count")
    @Operation(summary = "Get the total number of cpus")
    public Long count() {
        return this.cpuService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all cpus paged")
    @ApiResponse(responseCode = "200", description = "Cpus obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CpuDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1000") int size) {
        List<CpuDTO> content = this.cpuService.findAll(page, size);
        long totalElements = this.cpuService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a cpu by ID")
    @ApiResponse(responseCode = "200", description = "Cpu found.")
    @ApiResponse(responseCode = "404", description = "Cpu not found.")
    public CpuDTO findById(@PathVariable int id) {
        CpuDTO cpu = this.cpuService.findById(id);

        if (cpu == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return cpu;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new cpu")
    @ApiResponse(responseCode = "201", description = "Cpu created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Cpu name is duplicated.")
    public CpuDTO create(@RequestBody CpuDTO cpu) {
        validateComponentType(cpu);

        return this.cpuService.create(cpu);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a cpu by ID")
    @ApiResponse(responseCode = "204", description = "Cpu updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Cpu name is duplicated.")
    public void updateCpu(@PathVariable int id, @RequestBody CpuDTO cpu) {
        if (id != cpu.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(cpu);

        this.cpuService.update(cpu);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a cpu by ID")
    @ApiResponse(responseCode = "204", description = "Cpu deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Cpu cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody CpuDTO cpu) {
        if (id != cpu.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.cpuService.delete(cpu);
    }

    private void validateComponentType(CpuDTO cpu) {
        ComponentTypeDTO componentType = componentTypeService.findById(cpu.getComponentTypeDTO().getId());

        if (!"/cpus".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
