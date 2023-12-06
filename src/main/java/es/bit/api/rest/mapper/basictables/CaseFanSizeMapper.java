package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.CaseFanSize;
import es.bit.api.rest.dto.basictables.CaseFanSizeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CaseFanSizeMapper {
    public static CaseFanSizeDTO toDTO(CaseFanSize caseFanSize) {
        CaseFanSizeDTO caseFanSizeDTO = new CaseFanSizeDTO();

        if (caseFanSize != null) {
            caseFanSizeDTO.setId(caseFanSize.getId());
            caseFanSizeDTO.setName(caseFanSize.getName());
        }

        return caseFanSizeDTO;
    }


    public static CaseFanSizeDTO toDTO(Optional<CaseFanSize> caseFanSizeOptional) {
        return caseFanSizeOptional.map(CaseFanSizeMapper::toDTO).orElse(null);
    }


    public static List<CaseFanSizeDTO> toDTO(List<CaseFanSize> caseFanSizes) {
        List<CaseFanSizeDTO> caseFanSizesDTO = new ArrayList<>();

        if (caseFanSizes == null) {
            return caseFanSizesDTO;
        }

        for (CaseFanSize caseFanSize : caseFanSizes) {
            caseFanSizesDTO.add(CaseFanSizeMapper.toDTO(caseFanSize));
        }

        return caseFanSizesDTO;
    }


    public static CaseFanSize toBD(CaseFanSizeDTO caseFanSizeDTO) {
        CaseFanSize caseFanSize = new CaseFanSize();

        if (caseFanSizeDTO != null) {
            caseFanSize.setId(caseFanSizeDTO.getId());
            caseFanSize.setName(caseFanSizeDTO.getName());
        }

        return caseFanSize;
    }
}
