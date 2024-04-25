package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.basictables.ComponentType;
import es.bit.api.persistence.model.componenttables.Component;
import es.bit.api.persistence.repository.jpa.componenttables.*;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.dto.componenttables.*;
import es.bit.api.rest.mapper.basictables.ComponentTypeMapper;
import es.bit.api.rest.mapper.componenttables.*;
import es.bit.api.rest.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ComponentService implements GenericService<ComponentDTO, Component, Integer> {
    private final IComponentJpaRepository componentJPARepository;
    private final ICableJpaRepository cableJPARepository;
    private final ICaseFanJpaRepository caseFanJPARepository;
    private final ICaseJpaRepository caseJPARepository;
    private final ICpuCoolerJpaRepository cpuCoolerJPARepository;
    private final ICpuJpaRepository cpuJpaRepository;
    private final IGpuJpaRepository gpuJPARepository;
    private final IMotherboardJpaRepository motherboardJPARepository;
    private final IPowerSupplyJpaRepository powerSupplyJPARepository;
    private final IRamMemoryJpaRepository ramMemoryJPARepository;
    private final IStorageJpaRepository storageJPARepository;


    @Autowired
    public ComponentService(IComponentJpaRepository componentJPARepository, ICableJpaRepository cableJPARepository, ICaseFanJpaRepository caseFanJPARepository, ICaseJpaRepository caseJPARepository, ICpuCoolerJpaRepository cpuCoolerJPARepository, ICpuJpaRepository cpuJpaRepository, IGpuJpaRepository gpuJPARepository, IMotherboardJpaRepository motherboardJPARepository, IPowerSupplyJpaRepository powerSupplyJPARepository, IRamMemoryJpaRepository ramMemoryJPARepository, IStorageJpaRepository storageJPARepository) {
        this.componentJPARepository = componentJPARepository;
        this.cableJPARepository = cableJPARepository;
        this.caseFanJPARepository = caseFanJPARepository;
        this.caseJPARepository = caseJPARepository;
        this.cpuCoolerJPARepository = cpuCoolerJPARepository;
        this.cpuJpaRepository = cpuJpaRepository;
        this.gpuJPARepository = gpuJPARepository;
        this.motherboardJPARepository = motherboardJPARepository;
        this.powerSupplyJPARepository = powerSupplyJPARepository;
        this.ramMemoryJPARepository = ramMemoryJPARepository;
        this.storageJPARepository = storageJPARepository;
    }


    @Override
    public Long count() {
        return this.componentJPARepository.count();
    }

    @Override
    public ComponentDTO findById(Integer id) {
        Optional<Component> component = this.componentJPARepository.findById(id);

        if (component.isEmpty()) {
            return null;
        }

        return ComponentMapper.toDTO(component);
    }

    public List<ComponentDTO> findComponentsByIds(List<Integer> ids) {
        List<ComponentDTO> components = new ArrayList<>();
        for (Integer id : ids) {
            Optional<Component> componentOptional = componentJPARepository.findById(id);
            componentOptional.ifPresent(component -> {
                ComponentDTO componentDTO = ComponentMapper.toDTO(component);
                componentDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(component.getComponentType()));

                ComponentType componentType = component.getComponentType();
                if (componentType != null) {
                    switch (componentType.getName()) {
                        case "Cable":
                            CableDTO cableDTO = CableMapper.toDTO(cableJPARepository.findById(id), true);
                            components.add(cableDTO);
                            break;
                        case "Case Fan":
                            CaseFanDTO caseFanDTO = CaseFanMapper.toDTO(caseFanJPARepository.findById(id));
                            components.add(caseFanDTO);
                            break;
                        case "Case":
                            CaseDTO caseDTO = CaseMapper.toDTO(caseJPARepository.findById(id), true, true);
                            components.add(caseDTO);
                            break;
                        case "CPU Cooler":
                            CpuCoolerDTO cpuCoolerDTO = CpuCoolerMapper.toDTO(cpuCoolerJPARepository.findById(id), true);
                            components.add(cpuCoolerDTO);
                            break;
                        case "CPU":
                            CpuDTO cpuDTO = CpuMapper.toDTO(cpuJpaRepository.findById(id));
                            components.add(cpuDTO);
                            break;
                        case "GPU":
                            GpuDTO gpuDTO = GpuMapper.toDTO(gpuJPARepository.findById(id));
                            components.add(gpuDTO);
                            break;
                        case "Motherboard":
                            MotherboardDTO motherboardDTO = MotherboardMapper.toDTO(motherboardJPARepository.findById(id), true);
                            components.add(motherboardDTO);
                            break;
                        case "Power Supply":
                            PowerSupplyDTO powerSupplyDTO = PowerSupplyMapper.toDTO(powerSupplyJPARepository.findById(id));
                            components.add(powerSupplyDTO);
                            break;
                        case "Memory":
                            RamMemoryDTO ramMemoryDTO = RamMemoryMapper.toDTO(ramMemoryJPARepository.findById(id));
                            components.add(ramMemoryDTO);
                            break;
                        case "Storage":
                            StorageDTO storageDTO = StorageMapper.toDTO(storageJPARepository.findById(id));
                            components.add(storageDTO);
                            break;
                        default:
                            components.add(componentDTO);
                            break;
                    }
                } else {
                    components.add(componentDTO);
                }
            });
        }

        return components;
    }

    @Cacheable("components")
    @Override
    public List<ComponentDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<Component> cpuPage = this.componentJPARepository.findAll(getSpecification(filters), pageable);
        return ComponentMapper.toDTO(cpuPage.getContent());
    }

    @Override
    public ComponentDTO create(ComponentDTO componentDTO) {
        Component component = ComponentMapper.toBD(componentDTO);
        component = this.componentJPARepository.save(component);

        return ComponentMapper.toDTO(component);
    }

    @Override
    public void update(ComponentDTO componentDTO) {
        Component component = ComponentMapper.toBD(componentDTO);
        this.componentJPARepository.save(component);

        ComponentMapper.toDTO(component);
    }

    @Override
    public void delete(ComponentDTO componentDTO) {
        Component component = ComponentMapper.toBD(componentDTO);
        this.componentJPARepository.delete(component);
    }

    @Override
    public Specification<Component> getSpecification(Map<String, String> filters) {
        return GenericService.super.getSpecification(filters);
    }


    @Override
    public Map<String, Double> getPriceRange() {
        return Collections.emptyMap();
    }

    @Override
    public Set<ManufacturerDTO> getManufacturers() {
        return Collections.emptySet();
    }
}