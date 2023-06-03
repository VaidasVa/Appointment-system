package acn.intern.appointmentservice.business.service;

import acn.intern.appointmentservice.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<Room> getAllRooms();
    Optional<Room> getRoomById(Integer id);

    Room postRoom(Room room);

    Room updateRoom(Integer id, Room room);

    void deleteRoom(Integer id);
}
