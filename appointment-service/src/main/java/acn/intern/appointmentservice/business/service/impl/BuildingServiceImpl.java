package acn.intern.appointmentservice.business.service.impl;

import acn.intern.appointmentservice.business.mappers.BuildingMapper;
import acn.intern.appointmentservice.business.repository.BuildingRepository;
import acn.intern.appointmentservice.business.repository.impl.BuildingDAO;
import acn.intern.appointmentservice.business.service.BuildingService;
import acn.intern.appointmentservice.model.Building;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository repository;
    private final BuildingMapper mapper;

    @Override
    public List<Building> getAllBuildings() {
        List<BuildingDAO> buildingDAOs = repository.findAll();
        log.info("Found buildings: {}", buildingDAOs.size());
        return buildingDAOs.stream().map(mapper::buildingDAOToBuilding).collect(Collectors.toList());
    }

    @Override
    public Optional<Building> getBuildingById(Integer id) {
        Optional<BuildingDAO> buildingDAO = repository.findById(id);
        if (buildingDAO.isEmpty()) {
            log.error("Building with id {} not found", id);
            return Optional.empty();
        } else return Optional.of(mapper.buildingDAOToBuilding(buildingDAO.get()));
    }

    @Override
    public Building postBuilding(Building building) {
        BuildingDAO newBuildingDAO = repository.save(mapper.buildingToBuildingDAO(building));
        Building newBuilding = mapper.buildingDAOToBuilding(newBuildingDAO);
        log.info("Building saved: {}", newBuilding);
        return newBuilding;
    }

    @Override
    public Building updateBuilding(Integer id, Building building) {
        building.setId(id);
        BuildingDAO updatedBuildingDAO = repository.save(mapper.buildingToBuildingDAO(building));
        Building updatedBuilding = mapper.buildingDAOToBuilding(updatedBuildingDAO);
        log.info("Updated building: {}", updatedBuilding);
        return updatedBuilding;
    }

    @Override
    public void deleteBuilding(Integer id) {
        log.info("Deleting building: {}", id);
        repository.deleteById(id);
    }
}
