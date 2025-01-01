package es.bit.api.rest.controller.components;

import es.bit.api.persistence.model.components.CaseFan;
import es.bit.api.rest.controller.GenericController;
import es.bit.api.rest.dto.components.CaseFanDTO;
import es.bit.api.rest.service.components.CaseFanService;
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
@RequestMapping("/case_fans")
@Tag(name = "Case Fans Controller", description = "Related operations with case fans")
public class CaseFansController extends GenericController<CaseFanDTO, CaseFan, Integer> {
    @Autowired
    public CaseFansController(CaseFanService caseFanService) {
        super(caseFanService);
    }


    @Override
    @Operation(summary = "Get all case fans paged")
    @ApiResponse(responseCode = "200", description = "CaseFans obtained correctly.")
    @ApiResponse(responseCode = "412", description = "Error getting the selected page.")
    public PagedResponse<CaseFanDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam Map<String, String> filters
    ) {
        return super.findAll("caseFans", page, size, sortBy, sortDir, filters);
    }
}
