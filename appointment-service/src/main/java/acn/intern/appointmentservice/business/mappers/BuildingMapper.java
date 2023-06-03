package acn.intern.appointmentservice.business.mappers;

import acn.intern.appointmentservice.business.repository.impl.BuildingDAO;
import acn.intern.appointmentservice.model.Building;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuildingMapper {

    Building buildingDAOToBuilding(BuildingDAO buildingDAO);
    BuildingDAO buildingToBuildingDAO(Building building);
}
