package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.MotherboardFormFactor;
import es.bit.api.rest.dto.basictables.MotherboardFormFactorDTO;
import es.bit.api.rest.mapper.componenttables.CaseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MotherboardFormFactorMapper {
    public static MotherboardFormFactorDTO toDTO(MotherboardFormFactor motherboardFormFactor, Boolean withCases) {
        MotherboardFormFactorDTO motherboardFormFactorDTO = new MotherboardFormFactorDTO();

        if (motherboardFormFactor != null) {
            motherboardFormFactorDTO.setId(motherboardFormFactor.getId());
            motherboardFormFactorDTO.setName(motherboardFormFactor.getName());

            if (withCases) {
                motherboardFormFactorDTO.setCases(CaseMapper.toDTO(motherboardFormFactor.getCases(), false, false));
            }
        }

        return motherboardFormFactorDTO;
    }


    public static MotherboardFormFactorDTO toDTO(Optional<MotherboardFormFactor> motherboardFormFactorOptional, Boolean withCases) {
        return motherboardFormFactorOptional.map(motherboardFormFactor -> toDTO(motherboardFormFactor, withCases)).orElse(null);
    }


    public static List<MotherboardFormFactorDTO> toDTO(List<MotherboardFormFactor> motherboardFormFactors, Boolean withCases) {
        List<MotherboardFormFactorDTO> motherboardFormFactorsDTO = new ArrayList<>();

        if (motherboardFormFactors == null) {
            return motherboardFormFactorsDTO;
        }

        for (MotherboardFormFactor motherboardFormFactor : motherboardFormFactors) {
            motherboardFormFactorsDTO.add(MotherboardFormFactorMapper.toDTO(motherboardFormFactor, withCases));
        }

        return motherboardFormFactorsDTO;
    }


    public static MotherboardFormFactor toBD(MotherboardFormFactorDTO motherboardFormFactorDTO, Boolean withCases) {
        MotherboardFormFactor motherboardFormFactor = new MotherboardFormFactor();

        if (motherboardFormFactorDTO != null) {
            motherboardFormFactor.setId(motherboardFormFactorDTO.getId());
            motherboardFormFactor.setName(motherboardFormFactorDTO.getName());

            if (withCases) {
                motherboardFormFactor.setCases(CaseMapper.toBD(motherboardFormFactorDTO.getCases(), false, false));
            }
        }

        return motherboardFormFactor;
    }

    public static List<MotherboardFormFactor> toBD(List<MotherboardFormFactorDTO> motherboardFormFactorsDTO, Boolean withCases) {
        List<MotherboardFormFactor> motherboardFormFactors = new ArrayList<>();

        for (MotherboardFormFactorDTO motherboardFormFactorDTO : motherboardFormFactorsDTO) {
            motherboardFormFactors.add(MotherboardFormFactorMapper.toBD(motherboardFormFactorDTO, withCases));
        }

        return motherboardFormFactors;
    }
}
