package es.bit.api.rest.controller.components;

import es.bit.api.persistence.model.components.Cable;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.components.CableDTO;
import es.bit.api.rest.service.components.CableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cables")
@Tag(name = "Cables Controller", description = "Related operations with cables")
public class CablesController extends GenericController<CableDTO, Cable, Integer> {
    @Autowired
    public CablesController(CableService cableService) {
        super(cableService);
    }


    @Override
    @Operation(summary = "Get all cables paged")
    @ApiResponse(responseCode = "200", description = "Cables obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public Page<CableDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        return super.findAll("cables", page, size, sortBy, sortDir, filters);
    }
}
