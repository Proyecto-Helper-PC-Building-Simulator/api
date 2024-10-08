package es.bit.api.rest.controller.componenttables;

import es.bit.api.persistence.model.componenttables.CpuCooler;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.dto.componenttables.CpuCoolerDTO;
import es.bit.api.rest.service.basictables.ComponentTypeService;
import es.bit.api.rest.service.componenttables.CpuCoolerService;
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
@RequestMapping("/cpu_coolers")
@Tag(name = "Cpu Coolers Controller", description = "Related operations with cpu coolers")
public class CpuCoolersController extends GenericController<CpuCoolerDTO, CpuCooler, Integer> {
    private final ComponentTypeService componentTypeService;


    @Autowired
    public CpuCoolersController(CpuCoolerService cpuCoolerService, ComponentTypeService componentTypeService) {
        super(cpuCoolerService);
        this.componentTypeService = componentTypeService;
    }


    @Override
    @Operation(summary = "Get the total number of cpu coolers")
    public Long count() {
        return super.count();
    }

    @Override
    @Operation(summary = "Get all cpu coolers paged")
    @ApiResponse(responseCode = "200", description = "CpuCoolers obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CpuCoolerDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        return super.findAll(page, size, sortBy, sortDir, filters);
    }

    @Operation(summary = "Get a cpu cooler by ID")
    @ApiResponse(responseCode = "200", description = "Cpu cooler found.")
    @ApiResponse(responseCode = "404", description = "Cpu cooler not found.")
    public CpuCoolerDTO findById(@PathVariable int id) {
        return super.findById(id);
    }

    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new cpu cooler")
    @ApiResponse(responseCode = "201", description = "Cpu cooler created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Cpu cooler name is duplicated.")
    public CpuCoolerDTO create(@RequestBody CpuCoolerDTO cpuCooler) {
        validateComponentType(cpuCooler);

        return super.create(cpuCooler);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a cpu cooler by ID")
    @ApiResponse(responseCode = "204", description = "Cpu cooler updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Cpu cooler name is duplicated.")
    public void update(@PathVariable int id, @RequestBody CpuCoolerDTO cpuCooler) {
        if (id != cpuCooler.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(cpuCooler);

        super.update(id, cpuCooler);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a cpu cooler by ID")
    @ApiResponse(responseCode = "204", description = "Cpu cooler deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Cpu cooler cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody CpuCoolerDTO cpuCooler) {
        if (id != cpuCooler.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in delete query.");
        }

        super.delete(id);
    }

    @Override
    @GetMapping("/price-range")
    @Operation(summary = "Get the highest and lowest price of CPUs")
    public Map<String, Double> getPriceRange() {
        return super.getPriceRange();
    }

    @Override
    @GetMapping("/manufacturers")
    @Operation(summary = "Get a list of manufacturers without duplicates")
    public Set<ManufacturerDTO> getManufacturers() {
        return super.getManufacturers();
    }


    @Override
    protected void validateComponentType(CpuCoolerDTO cpuCooler) {
        ComponentTypeDTO componentType = componentTypeService.findById(cpuCooler.getComponentTypeDTO().getId());

        if (!"/cpu_coolers".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
