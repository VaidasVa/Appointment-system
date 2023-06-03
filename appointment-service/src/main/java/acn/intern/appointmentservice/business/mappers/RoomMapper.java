package acn.intern.appointmentservice.business.mappers;

import acn.intern.appointmentservice.business.repository.impl.RoomDAO;
import acn.intern.appointmentservice.model.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BuildingMapper.class)
public interface RoomMapper {

    Room roomDAOToRoom(RoomDAO roomDAO);
    RoomDAO roomToRoomDAO(Room room);
}
