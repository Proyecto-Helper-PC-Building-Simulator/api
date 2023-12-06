package es.bit.api.rest.controller;

import es.bit.api.rest.dto.PowerSupplyFormFactorDTO;
import es.bit.api.rest.service.PowerSupplyFormFactorService;
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
@RequestMapping("/power_supply_form_factors")
@Tag(name = "Power Supply Form Factor Controller", description = "Related operations with power supply form factors")
public class PowerSupplyFormFactorsController {
    @Autowired
    PowerSupplyFormFactorService powerSupplyFormFactorService;


    @GetMapping("/count")
    @Operation(summary = "Get the total number of power supply form factors")
    public Long count() {
        return this.powerSupplyFormFactorService.count();
    }

    @GetMapping("")
    @Operation(summary = "Get all power supply form factors paged")
    @ApiResponse(responseCode = "200", description = "Power supply form factors obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<PowerSupplyFormFactorDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        List<PowerSupplyFormFactorDTO> content = this.powerSupplyFormFactorService.findAll(page, size);
        long totalElements = this.powerSupplyFormFactorService.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page >= totalPages) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Page does not exist.");
        }

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a power supply form factor by ID")
    @ApiResponse(responseCode = "200", description = "Power supply form factor found.")
    @ApiResponse(responseCode = "404", description = "Power supply form factor not found.")
    public PowerSupplyFormFactorDTO findById(@PathVariable int id) {
        PowerSupplyFormFactorDTO powerSupplyFormFactor = this.powerSupplyFormFactorService.findById(id);

        if (powerSupplyFormFactor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found.");
        }

        return powerSupplyFormFactor;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new power supply form factor")
    @ApiResponse(responseCode = "201", description = "Power supply form factor created.")
    @ApiResponse(responseCode = "500", description = "Power supply form factor name is duplicated.")
    public PowerSupplyFormFactorDTO create(@RequestBody PowerSupplyFormFactorDTO powerSupplyFormFactor) {
        return this.powerSupplyFormFactorService.create(powerSupplyFormFactor);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a power supply form factor by ID")
    @ApiResponse(responseCode = "204", description = "Power supply form factor updated correctly.")
    @ApiResponse(responseCode = "412", description = "Error in update query.")
    @ApiResponse(responseCode = "500", description = "Power supply form factor name is duplicated.")
    public void updatePowerSupplyFormFactor(@PathVariable int id, @RequestBody PowerSupplyFormFactorDTO powerSupplyFormFactor) {
        if (id != powerSupplyFormFactor.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        this.powerSupplyFormFactorService.update(powerSupplyFormFactor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a power supply form factor by ID")
    @ApiResponse(responseCode = "204", description = "Power supply form factor deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Power supply form factor cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody PowerSupplyFormFactorDTO powerSupplyFormFactor) {
        if (id != powerSupplyFormFactor.getId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        this.powerSupplyFormFactorService.delete(powerSupplyFormFactor);
    }
}
