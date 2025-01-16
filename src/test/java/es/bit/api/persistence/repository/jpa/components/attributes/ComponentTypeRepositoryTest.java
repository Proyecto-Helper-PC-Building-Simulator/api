package es.bit.api.persistence.repository.jpa.components.attributes;

import es.bit.api.persistence.model.components.attributes.ComponentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ComponentTypeRepositoryTest {
    @Autowired
    private IComponentTypeJPARepository componentTypeRepository;


    @Test
    void existsByNameIdentifier_shouldReturnTrueIfTypeExists() {
        ComponentType componentType = new ComponentType();
        componentType.setNameIdentifier("cpu");
        componentTypeRepository.save(componentType);

        boolean exists = componentTypeRepository.existsByNameIdentifier("cpu");

        Assertions.assertTrue(exists);
    }

    @Test
    void existsByNameIdentifier_shouldReturnFalseIfTypeDoesNotExist() {
        boolean exists = componentTypeRepository.existsByNameIdentifier("nonexistent");

        Assertions.assertFalse(exists);
    }
}