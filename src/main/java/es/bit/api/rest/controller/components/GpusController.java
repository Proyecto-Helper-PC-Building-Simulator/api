package es.bit.api.rest.controller.components;

import es.bit.api.persistence.model.components.Gpu;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.components.GpuDTO;
import es.bit.api.rest.service.components.GpuService;
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
@RequestMapping("/gpus")
@Tag(name = "Gpus Controller", description = "Related operations with gpus")
public class GpusController extends GenericController<GpuDTO, Gpu, Integer> {
    @Autowired
    public GpusController(GpuService gpuService) {
        super(gpuService);
    }


    @Override
    @Operation(summary = "Get all gpus paged")
    @ApiResponse(responseCode = "200", description = "Gpus obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<GpuDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
       return super.findAll("gpus", page, size, sortBy, sortDir, filters);
    }
}
