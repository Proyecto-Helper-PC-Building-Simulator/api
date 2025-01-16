package es.bit.api.rest.controller.components;

import es.bit.api.persistence.model.components.CpuCooler;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.components.CpuCoolerDTO;
import es.bit.api.rest.service.components.CpuCoolerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cpu_coolers")
@Tag(name = "Cpu Coolers Controller", description = "Related operations with cpu coolers")
public class CpuCoolersController extends GenericController<CpuCoolerDTO, CpuCooler, Integer> {
    @Autowired
    public CpuCoolersController(CpuCoolerService cpuCoolerService) {
        super(cpuCoolerService);
    }


    @Override
    @Operation(summary = "Get all cpu coolers paged")
    @ApiResponse(responseCode = "200", description = "CpuCoolers obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        return super.findAll("cpuCoolers", page, size, sortBy, sortDir, filters);
    }
}
