package acn.intern.appointmentservice.business.service.impl;

import acn.intern.appointmentservice.business.mappers.BuildingMapper;
import acn.intern.appointmentservice.business.repository.BuildingRepository;
import acn.intern.appointmentservice.business.repository.impl.BuildingDAO;
import acn.intern.appointmentservice.model.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BuildingServiceImplTest {

    private Building building;

    private BuildingDAO buildingDAO;

    @Mock
    BuildingRepository repository;

    @Mock
    BuildingMapper mapper;

    @InjectMocks
    BuildingServiceImpl service;

    @BeforeEach
    void initializeValues() {
        building = createBuilding();
        buildingDAO = createBuildingDAO();
    }

    @Test
    void getAllBuildings() {
        when(repository.findAll()).thenReturn(List.of(buildingDAO)).thenReturn(new ArrayList<>());
        when(mapper.buildingDAOToBuilding(buildingDAO)).thenReturn(building);
        assertEquals(List.of(building), service.getAllBuildings());
        assertEquals(0, service.getAllBuildings().size());
        verify(repository, times(2)).findAll();
    }

    @Test
    void getBuildingById() {
        when(repository.findById(1))
                .thenReturn(Optional.ofNullable(buildingDAO))
                .thenReturn(Optional.empty());
        when(mapper.buildingDAOToBuilding(buildingDAO)).thenReturn(building);
        assertEquals(Optional.of(building), service.getBuildingById(1));
        assertEquals(Optional.empty(), service.getBuildingById(1));
        verify(repository, times(2)).findById(any());
    }

    @Test
    void postBuilding() {
        Building BuildingWithoutId = createBuilding();
        BuildingWithoutId.setId(null);
        BuildingDAO BuildingDAOWithoutId = createBuildingDAO();
        BuildingDAOWithoutId.setId(null);

        when(repository.save(BuildingDAOWithoutId)).thenReturn(buildingDAO);
        when(mapper.buildingToBuildingDAO(BuildingWithoutId)).thenReturn(BuildingDAOWithoutId);
        when(mapper.buildingDAOToBuilding(buildingDAO)).thenReturn(building);

        assertEquals(building, service.postBuilding(BuildingWithoutId));
        verify(repository, times(1)).save(BuildingDAOWithoutId);
    }

    @Test
    void updateBuilding() {
        when(repository.save(buildingDAO)).thenReturn(buildingDAO);
        when(mapper.buildingToBuildingDAO(building)).thenReturn(buildingDAO);
        when(mapper.buildingDAOToBuilding(buildingDAO)).thenReturn(building);

        assertEquals(building, service.updateBuilding(1, building));
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteBuilding() {
        assertDoesNotThrow(() -> service.deleteBuilding(1));
        verify(repository, times(1)).deleteById(any());
    }

    private Building createBuilding() {
        return Building.builder()
                .id(1)
                .name("Building One")
                .address("Broad st. 3")
                .city("Vilnius")
                .postCode("000123")
                .build();
    }

    private BuildingDAO createBuildingDAO() {
        return BuildingDAO.builder()
                .id(1)
                .name("Building One")
                .address("Broad st. 3")
                .city("Vilnius")
                .postCode("000123")
                .build();
    }
}