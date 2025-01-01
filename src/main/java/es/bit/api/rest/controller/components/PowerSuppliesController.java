package es.bit.api.rest.controller.components;

import es.bit.api.persistence.model.components.PowerSupply;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.components.PowerSupplyDTO;
import es.bit.api.rest.service.components.PowerSupplyService;
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
@RequestMapping("/power_supplies")
@Tag(name = "Power Supplies Controller", description = "Related operations with power supplies")
public class PowerSuppliesController extends GenericController<PowerSupplyDTO, PowerSupply, Integer> {
    @Autowired
    public PowerSuppliesController(PowerSupplyService powerSupplyService) {
        super(powerSupplyService);
    }


    @Override
    @Operation(summary = "Get all power supplies paged")
    @ApiResponse(responseCode = "200", description = "Power supplies obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<PowerSupplyDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        return super.findAll("powerSupplies", page, size, sortBy, sortDir, filters);
    }
}
