package es.bit.api.rest.controller.components;

import es.bit.api.persistence.model.components.PowerSupply;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.components.PowerSupplyDTO;
import es.bit.api.rest.dto.components.attributes.ComponentTypeDTO;
import es.bit.api.rest.service.components.PowerSupplyService;
import es.bit.api.rest.service.components.attributes.ComponentTypeService;
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
@RequestMapping("/power_supplies")
@Tag(name = "Power Supplies Controller", description = "Related operations with power supplies")
public class PowerSuppliesController extends GenericController<PowerSupplyDTO, PowerSupply, Integer> {
    private final ComponentTypeService componentTypeService;


    @Autowired
    public PowerSuppliesController(PowerSupplyService powerSupplyService, ComponentTypeService componentTypeService) {
        super(powerSupplyService);
        this.componentTypeService = componentTypeService;
    }


    @Override
    @Operation(summary = "Get the total number of power supplies")
    public Long count() {
        return super.count();
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

    @Operation(summary = "Get a power supply by ID")
    @ApiResponse(responseCode = "200", description = "Power supply found.")
    @ApiResponse(responseCode = "404", description = "Power supply not found.")
    public PowerSupplyDTO findById(@PathVariable int id) {
        return super.findById(id);
    }

    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new power supply")
    @ApiResponse(responseCode = "201", description = "Power supply created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Power supply name is duplicated.")
    public PowerSupplyDTO create(@RequestBody PowerSupplyDTO powerSupply) {
        validateComponentType(powerSupply);

        return super.create(powerSupply);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a power supply by ID")
    @ApiResponse(responseCode = "204", description = "Power supply updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Power supply name is duplicated.")
    public void update(@PathVariable int id, @RequestBody PowerSupplyDTO powerSupply) {
        if (id != powerSupply.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(powerSupply);

        super.update(id, powerSupply);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a power supply by ID")
    @ApiResponse(responseCode = "204", description = "Power supply deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Power supply cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody PowerSupplyDTO powerSupply) {
        if (id != powerSupply.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        super.delete(id);
    }


    @Override
    protected void validateComponentType(PowerSupplyDTO powerSupply) {
        ComponentTypeDTO componentType = componentTypeService.findById(powerSupply.getComponentTypeDTO().getId());

        if (!"/power_supplies".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
