package es.bit.api.rest.controller.componenttables;

import es.bit.api.persistence.model.componenttables.Cpu;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.dto.componenttables.CpuDTO;
import es.bit.api.rest.service.basictables.ComponentTypeService;
import es.bit.api.rest.service.componenttables.CpuService;
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
@RequestMapping("/cpus")
@Tag(name = "Cpus Controller", description = "Related operations with cpus")
public class CpusController extends GenericController<CpuDTO, Cpu, Integer> {
    private final ComponentTypeService componentTypeService;


    @Autowired
    public CpusController(CpuService cpuService, ComponentTypeService componentTypeService) {
        super(cpuService);
        this.componentTypeService = componentTypeService;
    }


    @Override
    @Operation(summary = "Get the total number of cpus")
    public Long count() {
        return super.count();
    }

    @Override
    @Operation(summary = "Get all cpus paged")
    @ApiResponse(responseCode = "200", description = "Cpus obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CpuDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        return super.findAll(page, size, sortBy, sortDir, filters);
    }

    @Operation(summary = "Get a cpu by ID")
    @ApiResponse(responseCode = "200", description = "Cpu found.")
    @ApiResponse(responseCode = "404", description = "Cpu not found.")
    public CpuDTO findById(@PathVariable int id) {
        return super.findById(id);
    }

    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a new cpu")
    @ApiResponse(responseCode = "201", description = "Cpu created.")
    @ApiResponse(responseCode = "412", description = "Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Cpu name is duplicated.")
    public CpuDTO create(@RequestBody CpuDTO cpu) {
        validateComponentType(cpu);

        return super.create(cpu);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity updated.")
    @Operation(summary = "Update a cpu by ID")
    @ApiResponse(responseCode = "204", description = "Cpu updated correctly.")
    @ApiResponse(responseCode = "412", description = "Component ID or Component Type ID not valid.")
    @ApiResponse(responseCode = "500", description = "Cpu name is duplicated.")
    public void update(@PathVariable int id, @RequestBody CpuDTO cpu) {
        if (id != cpu.getComponentId()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }

        validateComponentType(cpu);

        super.update(id, cpu);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entity deleted.")
    @Operation(summary = "Delete a cpu by ID")
    @ApiResponse(responseCode = "204", description = "Cpu deleted correctly.")
    @ApiResponse(responseCode = "412", description = "Error in delete query.")
    @ApiResponse(responseCode = "500", description = "Cpu cannot be deleted due to foreign keys.")
    public void delete(@PathVariable int id, @RequestBody CpuDTO cpu) {
        if (id != cpu.getComponentId()) {
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
    protected void validateComponentType(CpuDTO cpu) {
        ComponentTypeDTO componentType = componentTypeService.findById(cpu.getComponentTypeDTO().getId());

        if (!"/cpus".equals(componentType.getApiName())) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error in update query.");
        }
    }
}
