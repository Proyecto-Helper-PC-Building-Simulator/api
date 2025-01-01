package es.bit.api.rest.controller.components;

import es.bit.api.persistence.model.components.Storage;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.components.StorageDTO;
import es.bit.api.rest.service.components.StorageService;
import es.bit.api.utils.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/storages")
@Tag(name = "Storages Controller", description = "Related operations with storages")
public class StoragesController extends GenericController<StorageDTO, Storage, Integer> {
    @Autowired
    public StoragesController(StorageService storageService) {
        super(storageService);
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
       return super.findAll("storages", page, size, sortBy, sortDir, filters);
    }
}
