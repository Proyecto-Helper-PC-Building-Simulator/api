package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.MotherboardFormFactor;
import es.bit.api.rest.dto.basictables.MotherboardFormFactorDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MotherboardFormFactorMapper {
    public static MotherboardFormFactorDTO toDTO(MotherboardFormFactor motherboardFormFactor) {
        MotherboardFormFactorDTO motherboardFormFactorDTO = new MotherboardFormFactorDTO();

        if (motherboardFormFactor != null) {
            motherboardFormFactorDTO.setId(motherboardFormFactor.getId());
            motherboardFormFactorDTO.setName(motherboardFormFactor.getName());
        }

        return motherboardFormFactorDTO;
    }


    public static MotherboardFormFactorDTO toDTO(Optional<MotherboardFormFactor> motherboardFormFactorOptional) {
        return motherboardFormFactorOptional.map(MotherboardFormFactorMapper::toDTO).orElse(null);
    }


    public static List<MotherboardFormFactorDTO> toDTO(List<MotherboardFormFactor> motherboardFormFactors) {
        List<MotherboardFormFactorDTO> motherboardFormFactorsDTO = new ArrayList<>();

        if (motherboardFormFactors == null) {
            return motherboardFormFactorsDTO;
        }

        for (MotherboardFormFactor motherboardFormFactor : motherboardFormFactors) {
            motherboardFormFactorsDTO.add(MotherboardFormFactorMapper.toDTO(motherboardFormFactor));
        }

        return motherboardFormFactorsDTO;
    }


    public static MotherboardFormFactor toBD(MotherboardFormFactorDTO motherboardFormFactorDTO) {
        MotherboardFormFactor motherboardFormFactor = new MotherboardFormFactor();

        if (motherboardFormFactorDTO != null) {
            motherboardFormFactor.setId(motherboardFormFactorDTO.getId());
            motherboardFormFactor.setName(motherboardFormFactorDTO.getName());
        }

        return motherboardFormFactor;
    }
}
