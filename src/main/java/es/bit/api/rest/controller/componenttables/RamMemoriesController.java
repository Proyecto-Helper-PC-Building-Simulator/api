package es.bit.api.rest.controller.componenttables;

import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.componenttables.RamMemoryDTO;
import es.bit.api.rest.service.basictables.ComponentTypeService;
import es.bit.api.rest.service.componenttables.RamMemoryService;
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
@RequestMapping("/ram_memories")
@Tag(name = "Ram Memories Controller", description = "Related operations with ram memories")
public class RamMemoriesController {
    private final RamMemoryService ramMemoryService;
    private final ComponentTypeService componentTypeService;

    @Autowired
    public RamMemoriesController(RamMemoryService ramMemoryService, ComponentTypeService componentTypeService) {
        this.ramMemoryService = ramMemoryService;
        this.componentTypeService = componentTypeService;
    }


    @GetMapping("/count")
    @Operation(summary = "Get the total number of ram memories")
    public Long count() {
        return this.ramMemoryService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all ram memories paged")
    @ApiResponse(responseCode = "200", description = "Ram memories obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<RamMemoryDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        List<RamMemoryDTO> content = this.ramMemoryService.findAll(page, size);
        long totalElements = this.ramMemoryService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a case fan by ID")
    @ApiResponse(responseCode = "200", description = "Ram memory found.")
    @ApiResponse(responseCode = "404", description = "Ram memory not found.")
    public RamMemoryDTO findById(@PathVariable int id) {
        RamMemoryDTO ramMemory = this.ramMemoryService.findById(id);

        if (ramMemory == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return ramMemory;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new case fan")
    @ApiResponse(responseCode = "201", description = "Ram memory created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Ram memory name is duplicated.")
    public RamMemoryDTO create(@RequestBody RamMemoryDTO ramMemory) {
        validateComponentType(ramMemory);

        return this.ramMemoryService.create(ramMemory);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a case fan by ID")
    @ApiResponse(responseCode = "204", description = "Ram memory updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Ram memory name is duplicated.")
    public void updateRamMemory(@PathVariable int id, @RequestBody RamMemoryDTO ramMemory) {
        if (id != ramMemory.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(ramMemory);

        this.ramMemoryService.update(ramMemory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a case fan by ID")
    @ApiResponse(responseCode = "204", description = "Ram memory deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Ram memory cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody RamMemoryDTO ramMemory) {
        if (id != ramMemory.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.ramMemoryService.delete(ramMemory);
    }

    private void validateComponentType(RamMemoryDTO ramMemory) {
        ComponentTypeDTO componentType = componentTypeService.findById(ramMemory.getComponentTypeDTO().getId());

        if (!"/ram_memories".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
