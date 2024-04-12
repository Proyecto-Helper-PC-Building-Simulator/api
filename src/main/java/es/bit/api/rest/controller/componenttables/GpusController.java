package es.bit.api.rest.controller.componenttables;

import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.componenttables.GpuDTO;
import es.bit.api.rest.service.basictables.ComponentTypeService;
import es.bit.api.rest.service.componenttables.GpuService;
import es.bit.api.utils.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gpus")
@Tag(name = "Gpus Controller", description = "Related operations with gpus")
public class GpusController {
    private final GpuService gpuService;
    private final ComponentTypeService componentTypeService;

    @Autowired
    public GpusController(GpuService gpuService, ComponentTypeService componentTypeService) {
        this.gpuService = gpuService;
        this.componentTypeService = componentTypeService;
    }


    @GetMapping("/count")
    @Operation(summary = "Get the total number of gpus")
    public Long count() {
        return this.gpuService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all gpus paged")
    @ApiResponse(responseCode = "200", description = "Gpus obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<GpuDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        List<GpuDTO> content = this.gpuService.findAll(page, size, sortBy, sortDir, filters);
        long totalElements = this.gpuService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a gpu by ID")
    @ApiResponse(responseCode = "200", description = "Gpu found.")
    @ApiResponse(responseCode = "404", description = "Gpu not found.")
    public GpuDTO findById(@PathVariable int id) {
        GpuDTO gpu = this.gpuService.findById(id);

        if (gpu == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return gpu;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new gpu")
    @ApiResponse(responseCode = "201", description = "Gpu created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Gpu name is duplicated.")
    public GpuDTO create(@RequestBody GpuDTO gpu) {
        validateComponentType(gpu);

        return this.gpuService.create(gpu);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a gpu by ID")
    @ApiResponse(responseCode = "204", description = "Gpu updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Gpu name is duplicated.")
    public void updateGpu(@PathVariable int id, @RequestBody GpuDTO gpu) {
        if (id != gpu.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(gpu);

        this.gpuService.update(gpu);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a gpu by ID")
    @ApiResponse(responseCode = "204", description = "Gpu deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Gpu cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody GpuDTO gpu) {
        if (id != gpu.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.gpuService.delete(gpu);
    }

    private void validateComponentType(GpuDTO gpu) {
        ComponentTypeDTO componentType = componentTypeService.findById(gpu.getComponentTypeDTO().getId());

        if (!"/gpus".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
