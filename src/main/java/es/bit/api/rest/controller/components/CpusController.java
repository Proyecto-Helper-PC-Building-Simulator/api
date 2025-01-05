package es.bit.api.rest.controller.components;

import es.bit.api.persistence.model.components.Cpu;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.components.CpuDTO;
import es.bit.api.rest.service.components.CpuService;
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
@RequestMapping("/cpus")
@Tag(name = "Cpus Controller", description = "Related operations with cpus")
public class CpusController extends GenericController<CpuDTO, Cpu, Integer> {
    @Autowired
    public CpusController(CpuService cpuService) {
        super(cpuService);
    }


    @Override
    @Operation(summary = "Retrieve CPUs with optional filters",
            description = """
           Available filters:
           - `coresMin` and `coresMax`: Filter by number of cores (minimum and maximum).
           - `frequencyMin` and `frequencyMax`: Filter by frequency (minimum and maximum).
           - `wattageMin` and `wattageMax`: Filter by wattage (minimum and maximum).
           - `serie`: Filter by CPU series name (partial match).
           - `socket`: Filter by CPU socket name (partial match).
           - `priceMin` and `priceMax`: Filter by price range.
           - `level`: Filter by performance level.
           """)
    @ApiResponse(responseCode = "200", description = "Cpus obtained correctly.")
    public Page<CpuDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        return super.findAll("cpus", page, size, sortBy, sortDir, filters);
    }
}
