package es.bit.api.rest.service;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.persistence.model.components.attributes.ComponentType;
import es.bit.api.persistence.repository.jpa.components.IComponentJpaRepository;
import es.bit.api.rest.dto.components.ComponentDTO;
import es.bit.api.rest.service.components.ComponentService;
import es.bit.api.utils.PagedResponse;
import es.bit.api.utils.handlers.ComponentHandler;
import es.bit.api.utils.handlers.ComponentHandlerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private Map<String, String> filters;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(componentService, "repository", repository);
        ReflectionTestUtils.setField(componentService, "handlerFactory", handlerFactory);

        handlerMock = mock(ComponentHandler.class);

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

        filters = new HashMap<>();
        filters.put("name", "Test");
    }


    @Test
    void getComponentById_existentId_shouldReturnComponent() {
        int componentId = 1;
        String componentName = "Component 1";

        when(handlerMock.toDTO(mockComponent)).thenReturn(mockComponentDTO);
        when(repository.findById(componentId)).thenReturn(Optional.of(mockComponent));
        when(handlerFactory.getHandler(Mockito.anyString())).thenReturn(handlerMock);

        Optional<ComponentDTO> componentResponse = componentService.findById(componentId);

        assertTrue(componentResponse.isPresent());
        assertEquals(componentId, componentResponse.get().getComponentId());
        assertEquals(componentName, componentResponse.get().getName());
    }

    @Test
    void getComponentById_nonExistentId_shouldReturnEmpty() {
        int nonExistentId = -1;

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        Optional<ComponentDTO> componentResponse = componentService.findById(nonExistentId);

        assertTrue(componentResponse.isEmpty());
    }


    @Test
    void getAllComponents_noFilters_shouldReturnPagedResponse() {
        Pageable pageable = mock(Pageable.class);
        List<Component> componentList = new ArrayList<>();
        componentList.add(mockComponent);
        Page<Component> page = new PageImpl<>(componentList, pageable, componentList.size());

        when(repository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable))).thenReturn(page);
        when(handlerFactory.getHandler(Mockito.anyString())).thenReturn(handler);
        when(handler.toDTO(Mockito.any())).thenReturn(mockComponentDTO);

        PagedResponse<ComponentDTO> response = componentService.findAll("Cable", pageable, filters);

        assertNotNull(response);
        assertEquals(response.getContent().get(0).getComponentId(), mockComponent.getComponentId());

        verify(repository).findAll(Mockito.any(Specification.class), eq(pageable));
        verify(handlerFactory).getHandler(anyString());
        verify(handler).toDTO(mockComponent);
    }

    @Test
    void getAllComponents_withFilters_shouldReturnFilteredResults() {
        Pageable pageable = mock(Pageable.class);
        List<Component> componentList = new ArrayList<>();
        componentList.add(mockComponent);
        Page<Component> page = new PageImpl<>(componentList, pageable, componentList.size());

        when(repository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable))).thenReturn(page);
        when(handlerFactory.getHandler(Mockito.anyString())).thenReturn(handler);
        when(handler.toDTO(Mockito.any())).thenReturn(mockComponentDTO);

        filters.put("name", "Component");

        PagedResponse<ComponentDTO> response = componentService.findAll("Cable", pageable, filters);

        assertNotNull(response);
        assertFalse(response.getContent().isEmpty());

        verify(repository).findAll(Mockito.any(Specification.class), eq(pageable));
        verify(handlerFactory).getHandler("Cable");
        verify(handler).toDTO(mockComponent);
    }

    @Test
    void getAllComponents_withFilters_noResults_shouldReturnEmptyPagedResponse() {
        Pageable pageable = mock(Pageable.class);
        List<Component> componentList = new ArrayList<>();
        Page<Component> page = new PageImpl<>(componentList, pageable, 0);

        when(repository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable))).thenReturn(page);

        filters.put("name", "NonExistentComponent");

        PagedResponse<ComponentDTO> response = componentService.findAll("Cable", pageable, filters);

        assertNotNull(response);
        assertTrue(response.getContent().isEmpty());

        verify(repository).findAll(Mockito.any(Specification.class), eq(pageable));
    }
}