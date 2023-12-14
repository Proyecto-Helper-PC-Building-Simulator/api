package es.bit.api.rest.mapper.componenttables;

import es.bit.api.persistence.model.componenttables.Case;
import es.bit.api.rest.dto.componenttables.CaseDTO;
import es.bit.api.rest.mapper.basictables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CaseMapper {
    public static CaseDTO toDTO(Case caseObject, Boolean withMotherboardFormFactors) {
        CaseDTO caseObjectDTO = new CaseDTO();
        caseObjectDTO.setComponentId(caseObject.getComponentId());
        caseObjectDTO.setName(caseObject.getName());
        caseObjectDTO.setPrice(caseObject.getPrice());
        caseObjectDTO.setManufacturerDTO(ManufacturerMapper.toDTO(caseObject.getManufacturer()));
        caseObjectDTO.setLightingDTO(LightingMapper.toDTO(caseObject.getLighting()));
        caseObjectDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(caseObject.getComponentType()));

        caseObjectDTO.setMaxPsuLength(caseObject.getMaxPsuLength());
        caseObjectDTO.setMaxGpuLength(caseObject.getMaxGpuLength());
        caseObjectDTO.setMaxCpuFanHeight(caseObject.getMaxCpuFanHeight());
        caseObjectDTO.setCaseSize(CaseSizeMapper.toDTO(caseObject.getCaseSize()));
        caseObjectDTO.setCaseFanSize(CaseFanSizeMapper.toDTO(caseObject.getCaseFanSize()));

        if (withMotherboardFormFactors) {
            caseObjectDTO.setMotherboardFormFactors(MotherboardFormFactorMapper.toDTO(caseObject.getMotherboardFormFactors(), false));
        }

        return caseObjectDTO;
    }

    public static CaseDTO toDTO(Optional<Case> caseObjectOptional, Boolean withMotherboardFormFactors) {
        return caseObjectOptional.map(caseObject -> toDTO(caseObject, withMotherboardFormFactors)).orElse(null);
    }

    public static List<CaseDTO> toDTO(List<Case> caseObjects, Boolean withMotherboardFormFactors) {
        List<CaseDTO> caseObjectsDTO = new ArrayList<>();

        if (caseObjects == null)
            return caseObjectsDTO;

        for (Case caseObject : caseObjects) {
            caseObjectsDTO.add(CaseMapper.toDTO(caseObject, withMotherboardFormFactors));
        }

        return caseObjectsDTO;
    }

    public static Case toBD(CaseDTO caseObjectDTO, Boolean withMotherboardFormFactors) {
        Case caseObject = new Case();
        caseObject.setComponentId(caseObjectDTO.getComponentId());
        caseObject.setName(caseObjectDTO.getName());
        caseObject.setPrice(caseObjectDTO.getPrice());
        caseObject.setManufacturer(ManufacturerMapper.toBD(caseObjectDTO.getManufacturerDTO()));
        caseObject.setLighting(LightingMapper.toBD(caseObjectDTO.getLightingDTO()));
        caseObject.setComponentType(ComponentTypeMapper.toBD(caseObjectDTO.getComponentTypeDTO()));

        caseObject.setMaxPsuLength(caseObjectDTO.getMaxPsuLength());
        caseObject.setMaxGpuLength(caseObjectDTO.getMaxGpuLength());
        caseObject.setMaxCpuFanHeight(caseObjectDTO.getMaxCpuFanHeight());
        caseObject.setCaseSize(CaseSizeMapper.toBD(caseObjectDTO.getCaseSize()));
        caseObject.setCaseFanSize(CaseFanSizeMapper.toBD(caseObjectDTO.getCaseFanSize()));

        if (withMotherboardFormFactors) {
            caseObject.setMotherboardFormFactors(MotherboardFormFactorMapper.toBD(caseObjectDTO.getMotherboardFormFactors(), false));
        }

        return caseObject;
    }

    public static List<Case> toBD(List<CaseDTO> casesDTO, Boolean withMotherboardFormFactors) {
        List<Case> cases = new ArrayList<>();

        for (CaseDTO caseDTO : casesDTO) {
            cases.add(CaseMapper.toBD(caseDTO, withMotherboardFormFactors));
        }

        return cases;
    }
}
