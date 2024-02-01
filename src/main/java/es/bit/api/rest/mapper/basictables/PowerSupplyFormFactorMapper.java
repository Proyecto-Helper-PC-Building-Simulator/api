package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.PowerSupplyFormFactor;
import es.bit.api.rest.dto.basictables.PowerSupplyFormFactorDTO;
import es.bit.api.rest.mapper.componenttables.CaseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PowerSupplyFormFactorMapper {
    public static PowerSupplyFormFactorDTO toDTO(PowerSupplyFormFactor powerSupplyFormFactor, Boolean withCases) {
        PowerSupplyFormFactorDTO powerSupplyFormFactorDTO = new PowerSupplyFormFactorDTO();

        if (powerSupplyFormFactor != null) {
            powerSupplyFormFactorDTO.setId(powerSupplyFormFactor.getId());
            powerSupplyFormFactorDTO.setName(powerSupplyFormFactor.getName());

            if (withCases) {
                powerSupplyFormFactorDTO.setCases(CaseMapper.toDTO(powerSupplyFormFactor.getCases(), false, false));
            }
        }

        return powerSupplyFormFactorDTO;
    }


    public static PowerSupplyFormFactorDTO toDTO(Optional<PowerSupplyFormFactor> powerSupplyFormFactorOptional, Boolean withCases) {
        return powerSupplyFormFactorOptional.map(powerSupplyFormFactor -> toDTO(powerSupplyFormFactor, withCases)).orElse(null);
    }


    public static List<PowerSupplyFormFactorDTO> toDTO(List<PowerSupplyFormFactor> powerSupplyFormFactors, Boolean withCases) {
        List<PowerSupplyFormFactorDTO> powerSupplyFormFactorsDTO = new ArrayList<>();

        if (powerSupplyFormFactors == null) {
            return powerSupplyFormFactorsDTO;
        }

        for (PowerSupplyFormFactor powerSupplyFormFactor : powerSupplyFormFactors) {
            powerSupplyFormFactorsDTO.add(PowerSupplyFormFactorMapper.toDTO(powerSupplyFormFactor, withCases));
        }

        return powerSupplyFormFactorsDTO;
    }


    public static PowerSupplyFormFactor toBD(PowerSupplyFormFactorDTO powerSupplyFormFactorDTO, Boolean withCases) {
        PowerSupplyFormFactor powerSupplyFormFactor = new PowerSupplyFormFactor();

        if (powerSupplyFormFactorDTO != null) {
            powerSupplyFormFactor.setId(powerSupplyFormFactorDTO.getId());
            powerSupplyFormFactor.setName(powerSupplyFormFactorDTO.getName());

            if (withCases) {
                powerSupplyFormFactor.setCases(CaseMapper.toBD(powerSupplyFormFactorDTO.getCases(), false, false));
            }
        }

        return powerSupplyFormFactor;
    }

    public static List<PowerSupplyFormFactor> toBD(List<PowerSupplyFormFactorDTO> powerSupplyFormFactorsDTO, Boolean withCases) {
        List<PowerSupplyFormFactor> powerSupplyFormFactors = new ArrayList<>();

        for (PowerSupplyFormFactorDTO powerSupplyFormFactorDTO : powerSupplyFormFactorsDTO) {
            powerSupplyFormFactors.add(PowerSupplyFormFactorMapper.toBD(powerSupplyFormFactorDTO, withCases));
        }

        return powerSupplyFormFactors;
    }
}
