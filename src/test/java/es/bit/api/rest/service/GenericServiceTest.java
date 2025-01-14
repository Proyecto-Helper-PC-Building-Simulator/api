package es.bit.api.rest.service;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.persistence.model.components.attributes.ComponentType;
import es.bit.api.persistence.repository.jpa.components.IComponentJpaRepository;
import es.bit.api.rest.dto.components.ComponentDTO;
import es.bit.api.rest.service.components.ComponentService;
import es.bit.api.utils.handlers.ComponentHandler;
import es.bit.api.utils.handlers.ComponentHandlerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GenericServiceTest {
    @Mock
    private IComponentJpaRepository repository;

    @Mock
    private ComponentHandlerFactory<Component, ComponentDTO> handlerFactory;

    @Mock
    private ComponentHandler<Component, ComponentDTO> handler;


    @InjectMocks
    private ComponentService componentService;


    private static Component mockComponent;
    private static ComponentDTO mockComponentDTO;
    private static ComponentHandler<Component, ComponentDTO> handlerMock;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(componentService, "repository", repository);
        ReflectionTestUtils.setField(componentService, "handlerFactory", handlerFactory);

        handlerMock = Mockito.mock(ComponentHandler.class);

        ComponentType componentType = ComponentType.builder().nameIdentifier("Cable").build();
        mockComponent = Component.builder()
                .componentId(1)
                .name("Component 1")
                .componentType(componentType)
                .build();

        mockComponentDTO = ComponentDTO.builder()
                .componentId(1)
                .name("Component 1")
                .build();
    }


    @Test
    void getComponentById_existentId_shouldReturnComponent() {
        int componentId = 1;
        String componentName = "Component 1";

        Mockito.when(handlerMock.toDTO(mockComponent)).thenReturn(mockComponentDTO);
        Mockito.when(repository.findById(componentId)).thenReturn(Optional.of(mockComponent));
        Mockito.when(handlerFactory.getHandler(Mockito.anyString())).thenReturn(handlerMock);

        Optional<ComponentDTO> componentResponse = componentService.findById(componentId);

        Assertions.assertTrue(componentResponse.isPresent());
        Assertions.assertEquals(componentId, componentResponse.get().getComponentId());
        Assertions.assertEquals(componentName, componentResponse.get().getName());
    }

    @Test
    void getComponentById_nonExistentId_shouldReturnEmpty() {
        int nonExistentId = -1;

        Mockito.when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        Optional<ComponentDTO> componentResponse = componentService.findById(nonExistentId);

        Assertions.assertTrue(componentResponse.isEmpty());
    }


    @Test
    void findAll() {

    }

    void save() {
    }

    void update() {
    }

    void delete() {
    }

    void getSpecification() {
    }

    void addCommonPredicates() {
    }
}