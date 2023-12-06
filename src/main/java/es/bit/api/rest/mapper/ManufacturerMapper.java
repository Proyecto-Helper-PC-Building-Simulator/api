package es.bit.api.rest.mapper;

import es.bit.api.persistence.model.Manufacturer;
import es.bit.api.rest.dto.ManufacturerDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerMapper {
    public static ManufacturerDTO toDTO(Manufacturer manufacturer) {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();

        if (manufacturer != null) {
            manufacturerDTO.setId(manufacturer.getId());
            manufacturerDTO.setName(manufacturer.getName());
        }

        return manufacturerDTO;
    }


    public static ManufacturerDTO toDTO(Optional<Manufacturer> manufacturerOptional) {
        return manufacturerOptional.map(ManufacturerMapper::toDTO).orElse(null);
    }


    public static List<ManufacturerDTO> toDTO(List<Manufacturer> manufacturers) {
        List<ManufacturerDTO> manufacturersDTO = new ArrayList<>();

        if (manufacturers == null) {
            return manufacturersDTO;
        }

        for (Manufacturer manufacturer : manufacturers) {
            manufacturersDTO.add(ManufacturerMapper.toDTO(manufacturer));
        }

        return manufacturersDTO;
    }


    public static Manufacturer toBD(ManufacturerDTO manufacturerDTO) {
        Manufacturer manufacturer = new Manufacturer();

        if (manufacturerDTO != null) {
            manufacturer.setId(manufacturerDTO.getId());
            manufacturer.setName(manufacturerDTO.getName());
        }

        return manufacturer;
    }
}
