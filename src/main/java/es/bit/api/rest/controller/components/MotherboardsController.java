package es.bit.api.rest.controller.components;

import es.bit.api.persistence.model.components.Motherboard;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.components.MotherboardDTO;
import es.bit.api.rest.service.components.MotherboardService;
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
@RequestMapping("/motherboards")
@Tag(name = "Motherboards Controller", description = "Related operations with motherboards")
public class MotherboardsController extends GenericController<MotherboardDTO, Motherboard, Integer> {
    @Autowired
    public MotherboardsController(MotherboardService motherboardService) {
        super(motherboardService);
    }


    @Override
    @Operation(summary = "Get all motherboards paged")
    @ApiResponse(responseCode = "200", description = "Motherboards obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public Page<MotherboardDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        return super.findAll("motherboards", page, size, sortBy, sortDir, filters);
    }
}
