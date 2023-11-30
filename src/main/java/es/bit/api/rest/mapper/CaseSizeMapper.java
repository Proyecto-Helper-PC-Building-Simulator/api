package es.bit.api.rest.mapper;

import es.bit.api.persistence.model.CaseSize;
import es.bit.api.rest.dto.CaseSizeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CaseSizeMapper {
    public static CaseSizeDTO toDTO(CaseSize caseSize) {
        CaseSizeDTO caseSizeDTO = new CaseSizeDTO();

        if (caseSize != null) {
            caseSizeDTO.setId(caseSize.getId());
            caseSizeDTO.setName(caseSize.getName());
        }

        return caseSizeDTO;
    }


    public static CaseSizeDTO toDTO(Optional<CaseSize> caseSizeOptional) {
        return caseSizeOptional.map(CaseSizeMapper::toDTO).orElse(null);
    }


    public static List<CaseSizeDTO> toDTO(List<CaseSize> caseSizes) {
        List<CaseSizeDTO> caseSizesDTO = new ArrayList<>();

        if (caseSizes == null) {
            return caseSizesDTO;
        }

        for (CaseSize caseSize : caseSizes) {
            caseSizesDTO.add(CaseSizeMapper.toDTO(caseSize));
        }

        return caseSizesDTO;
    }


    public static CaseSize toBD(CaseSizeDTO caseSizeDTO) {
        CaseSize caseSize = new CaseSize();

        if (caseSizeDTO != null) {
            caseSize.setId(caseSizeDTO.getId());
            caseSize.setName(caseSizeDTO.getName());
        }

        return caseSize;
    }
}
