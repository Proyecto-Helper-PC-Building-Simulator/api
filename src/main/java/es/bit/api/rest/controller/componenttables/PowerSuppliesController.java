package es.bit.api.rest.controller.componenttables;

import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.componenttables.PowerSupplyDTO;
import es.bit.api.rest.service.basictables.ComponentTypeService;
import es.bit.api.rest.service.componenttables.PowerSupplyService;
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
@RequestMapping("/power_supplies")
@Tag(name = "Power Supplies Controller", description = "Related operations with power supplies")
public class PowerSuppliesController {
    private final PowerSupplyService powerSupplyService;
    private final ComponentTypeService componentTypeService;

    @Autowired
    public PowerSuppliesController(PowerSupplyService powerSupplyService, ComponentTypeService componentTypeService) {
        this.powerSupplyService = powerSupplyService;
        this.componentTypeService = componentTypeService;
    }


    @GetMapping("/count")
    @Operation(summary = "Get the total number of power supplies")
    public Long count() {
        return this.powerSupplyService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all power supplies paged")
    @ApiResponse(responseCode = "200", description = "Power supplies obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<PowerSupplyDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size) {
        List<PowerSupplyDTO> content = this.powerSupplyService.findAll(page, size);
        long totalElements = this.powerSupplyService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a power supply by ID")
    @ApiResponse(responseCode = "200", description = "Power supply found.")
    @ApiResponse(responseCode = "404", description = "Power supply not found.")
    public PowerSupplyDTO findById(@PathVariable int id) {
        PowerSupplyDTO powerSupply = this.powerSupplyService.findById(id);

        if (powerSupply == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return powerSupply;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new power supply")
    @ApiResponse(responseCode = "201", description = "Power supply created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Power supply name is duplicated.")
    public PowerSupplyDTO create(@RequestBody PowerSupplyDTO powerSupply) {
        validateComponentType(powerSupply);

        return this.powerSupplyService.create(powerSupply);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a power supply by ID")
    @ApiResponse(responseCode = "204", description = "Power supply updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Power supply name is duplicated.")
    public void updatePowerSupply(@PathVariable int id, @RequestBody PowerSupplyDTO powerSupply) {
        if (id != powerSupply.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(powerSupply);

        this.powerSupplyService.update(powerSupply);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a power supply by ID")
    @ApiResponse(responseCode = "204", description = "Power supply deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Power supply cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody PowerSupplyDTO powerSupply) {
        if (id != powerSupply.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.powerSupplyService.delete(powerSupply);
    }

    private void validateComponentType(PowerSupplyDTO powerSupply) {
        ComponentTypeDTO componentType = componentTypeService.findById(powerSupply.getComponentTypeDTO().getId());

        if (!"/power_supplies".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
