package es.bit.api.rest.controller.componenttables;

import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.componenttables.StorageDTO;
import es.bit.api.rest.service.basictables.ComponentTypeService;
import es.bit.api.rest.service.componenttables.StorageService;
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
@RequestMapping("/storages")
@Tag(name = "Storages Controller", description = "Related operations with storages")
public class StoragesController {
    private final StorageService storageService;
    private final ComponentTypeService componentTypeService;

    @Autowired
    public StoragesController(StorageService storageService, ComponentTypeService componentTypeService) {
        this.storageService = storageService;
        this.componentTypeService = componentTypeService;
    }


    @GetMapping("/count")
    @Operation(summary = "Get the total number of storages")
    public Long count() {
        return this.storageService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all storages paged")
    @ApiResponse(responseCode = "200", description = "Storages obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<StorageDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size) {
        List<StorageDTO> content = this.storageService.findAll(page, size);
        long totalElements = this.storageService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a storage by ID")
    @ApiResponse(responseCode = "200", description = "Storage found.")
    @ApiResponse(responseCode = "404", description = "Storage not found.")
    public StorageDTO findById(@PathVariable int id) {
        StorageDTO storage = this.storageService.findById(id);

        if (storage == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return storage;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new storage")
    @ApiResponse(responseCode = "201", description = "Storage created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Storage name is duplicated.")
    public StorageDTO create(@RequestBody StorageDTO storage) {
        validateComponentType(storage);

        return this.storageService.create(storage);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a storage by ID")
    @ApiResponse(responseCode = "204", description = "Storage updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Storage name is duplicated.")
    public void updateStorage(@PathVariable int id, @RequestBody StorageDTO storage) {
        if (id != storage.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(storage);

        this.storageService.update(storage);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a storage by ID")
    @ApiResponse(responseCode = "204", description = "Storage deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Storage cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody StorageDTO storage) {
        if (id != storage.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.storageService.delete(storage);
    }

    private void validateComponentType(StorageDTO storage) {
        ComponentTypeDTO componentType = componentTypeService.findById(storage.getComponentTypeDTO().getId());

        if (!"/storages".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
