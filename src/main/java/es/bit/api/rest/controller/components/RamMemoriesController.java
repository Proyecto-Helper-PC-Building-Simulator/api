package es.bit.api.rest.controller.components;

import es.bit.api.persistence.model.components.RamMemory;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.components.RamMemoryDTO;
import es.bit.api.rest.service.components.RamMemoryService;
import es.bit.api.utils.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/ram_memories")
@Tag(name = "Ram Memories Controller", description = "Related operations with ram memories")
public class RamMemoriesController extends GenericController<RamMemoryDTO, RamMemory, Integer> {
    @Autowired
    public RamMemoriesController(RamMemoryService ramMemoryService) {
        super(ramMemoryService);
    }


    @Override
    @Operation(summary = "Get the total number of ram memories")
    public Long count() {
        return super.count();
    }

    @Override
    @Operation(summary = "Get all ram memories paged")
    @ApiResponse(responseCode = "200", description = "Ram memories obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<RamMemoryDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        return super.findAll("ramMemories", page, size, sortBy, sortDir, filters);
    }

    @Operation(summary = "Get a case fan by ID")
    @ApiResponse(responseCode = "200", description = "Ram memory found.")
    @ApiResponse(responseCode = "404", description = "Ram memory not found.")
    public RamMemoryDTO findById(@PathVariable int id) {
        return super.findById(id);
    }

    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new case fan")
    @ApiResponse(responseCode = "201", description = "Ram memory created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Ram memory name is duplicated.")
    public RamMemoryDTO create(@RequestBody RamMemoryDTO ramMemory) {
        return super.create(ramMemory);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a case fan by ID")
    @ApiResponse(responseCode = "204", description = "Ram memory updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Ram memory name is duplicated.")
    public void update(@PathVariable int id, @RequestBody RamMemoryDTO ramMemory) {
        if (id != ramMemory.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        super.update(id, ramMemory);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a case fan by ID")
    @ApiResponse(responseCode = "204", description = "Ram memory deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Ram memory cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody RamMemoryDTO ramMemory) {
        if (id != ramMemory.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        super.delete(id);
    }
}
