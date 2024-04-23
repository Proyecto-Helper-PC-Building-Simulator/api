package es.bit.api.rest.controller.componenttables;

import es.bit.api.persistence.model.componenttables.Storage;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
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

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/storages")
@Tag(name = "Storages Controller", description = "Related operations with storages")
public class StoragesController extends GenericController<StorageDTO, Storage, Integer> {
    private final ComponentTypeService componentTypeService;


    @Autowired
    public StoragesController(StorageService storageService, ComponentTypeService componentTypeService) {
        super(storageService);
        this.componentTypeService = componentTypeService;
    }


    @Override
    @Operation(summary = "Get the total number of storages")
    public Long count() {
        return super.count();
    }

    @Override
    @Operation(summary = "Get all storages paged")
    @ApiResponse(responseCode = "200", description = "Storages obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<StorageDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
       return super.findAll(page, size, sortBy, sortDir, filters);
    }

    @Operation(summary = "Get a storage by ID")
    @ApiResponse(responseCode = "200", description = "Storage found.")
    @ApiResponse(responseCode = "404", description = "Storage not found.")
    public StorageDTO findById(@PathVariable int id) {
        return super.findById(id);
    }

    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new storage")
    @ApiResponse(responseCode = "201", description = "Storage created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Storage name is duplicated.")
    public StorageDTO create(@RequestBody StorageDTO storage) {
        validateComponentType(storage);

        return super.create(storage);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a storage by ID")
    @ApiResponse(responseCode = "204", description = "Storage updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Storage name is duplicated.")
    public void update(@PathVariable int id, @RequestBody StorageDTO storage) {
        if (id != storage.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(storage);

        super.update(id, storage);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a storage by ID")
    @ApiResponse(responseCode = "204", description = "Storage deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Storage cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody StorageDTO storage) {
        if (id != storage.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        super.delete(id);
    }

    @Override
    @Operation(summary = "Get the highest and lowest price of CPUs")
    public Map<String, Double> getPriceRange() {
        return super.getPriceRange();
    }

    @Override
    @Operation(summary = "Get a list of manufacturers without duplicates")
    public Set<ManufacturerDTO> getManufacturers() {
        return super.getManufacturers();
    }


    @Override
    protected void validateComponentType(StorageDTO storage) {
        ComponentTypeDTO componentType = componentTypeService.findById(storage.getComponentTypeDTO().getId());

        if (!"/storages".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
