package acn.intern.appointmentservice.business.service;

import acn.intern.appointmentservice.model.Building;

import java.util.List;
import java.util.Optional;

public interface BuildingService {

    List<Building> getAllBuildings();
    Optional<Building> getBuildingById(Integer id);

    Building postBuilding(Building building);

    Building updateBuilding(Integer id, Building building);

    void deleteBuilding(Integer id);
}
