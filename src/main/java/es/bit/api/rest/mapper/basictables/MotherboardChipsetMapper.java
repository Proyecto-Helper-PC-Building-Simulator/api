package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.MotherboardChipset;
import es.bit.api.rest.dto.basictables.MotherboardChipsetDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MotherboardChipsetMapper {
    public static MotherboardChipsetDTO toDTO(MotherboardChipset motherboardChipset) {
        MotherboardChipsetDTO motherboardChipsetDTO = new MotherboardChipsetDTO();

        if (motherboardChipset != null) {
            motherboardChipsetDTO.setId(motherboardChipset.getId());
            motherboardChipsetDTO.setName(motherboardChipset.getName());
        }

        return motherboardChipsetDTO;
    }


    public static MotherboardChipsetDTO toDTO(Optional<MotherboardChipset> motherboardChipsetOptional) {
        return motherboardChipsetOptional.map(MotherboardChipsetMapper::toDTO).orElse(null);
    }


    public static List<MotherboardChipsetDTO> toDTO(List<MotherboardChipset> motherboardChipsets) {
        List<MotherboardChipsetDTO> motherboardChipsetsDTO = new ArrayList<>();

        if (motherboardChipsets == null) {
            return motherboardChipsetsDTO;
        }

        for (MotherboardChipset motherboardChipset : motherboardChipsets) {
            motherboardChipsetsDTO.add(MotherboardChipsetMapper.toDTO(motherboardChipset));
        }

        return motherboardChipsetsDTO;
    }


    public static MotherboardChipset toBD(MotherboardChipsetDTO motherboardChipsetDTO) {
        MotherboardChipset motherboardChipset = new MotherboardChipset();

        if (motherboardChipsetDTO != null) {
            motherboardChipset.setId(motherboardChipsetDTO.getId());
            motherboardChipset.setName(motherboardChipsetDTO.getName());
        }

        return motherboardChipset;
    }
}
