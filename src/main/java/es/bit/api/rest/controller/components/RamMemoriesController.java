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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
