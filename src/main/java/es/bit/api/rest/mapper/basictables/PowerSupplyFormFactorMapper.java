package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.PowerSupplyFormFactor;
import es.bit.api.rest.dto.basictables.PowerSupplyFormFactorDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PowerSupplyFormFactorMapper {
    public static PowerSupplyFormFactorDTO toDTO(PowerSupplyFormFactor powerSupplyFormFactor) {
        PowerSupplyFormFactorDTO powerSupplyFormFactorDTO = new PowerSupplyFormFactorDTO();

        if (powerSupplyFormFactor != null) {
            powerSupplyFormFactorDTO.setId(powerSupplyFormFactor.getId());
            powerSupplyFormFactorDTO.setName(powerSupplyFormFactor.getName());
        }

        return powerSupplyFormFactorDTO;
    }


    public static PowerSupplyFormFactorDTO toDTO(Optional<PowerSupplyFormFactor> powerSupplyFormFactorOptional) {
        return powerSupplyFormFactorOptional.map(PowerSupplyFormFactorMapper::toDTO).orElse(null);
    }


    public static List<PowerSupplyFormFactorDTO> toDTO(List<PowerSupplyFormFactor> powerSupplyFormFactors) {
        List<PowerSupplyFormFactorDTO> powerSupplyFormFactorsDTO = new ArrayList<>();

        if (powerSupplyFormFactors == null) {
            return powerSupplyFormFactorsDTO;
        }

        for (PowerSupplyFormFactor powerSupplyFormFactor : powerSupplyFormFactors) {
            powerSupplyFormFactorsDTO.add(PowerSupplyFormFactorMapper.toDTO(powerSupplyFormFactor));
        }

        return powerSupplyFormFactorsDTO;
    }


    public static PowerSupplyFormFactor toBD(PowerSupplyFormFactorDTO powerSupplyFormFactorDTO) {
        PowerSupplyFormFactor powerSupplyFormFactor = new PowerSupplyFormFactor();

        if (powerSupplyFormFactorDTO != null) {
            powerSupplyFormFactor.setId(powerSupplyFormFactorDTO.getId());
            powerSupplyFormFactor.setName(powerSupplyFormFactorDTO.getName());
        }

        return powerSupplyFormFactor;
    }
}
