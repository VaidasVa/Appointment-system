package acn.intern.appointmentservice.business.service.impl;

import acn.intern.appointmentservice.business.mappers.RoomMapper;
import acn.intern.appointmentservice.business.repository.RoomRepository;
import acn.intern.appointmentservice.business.repository.impl.RoomDAO;
import acn.intern.appointmentservice.business.service.RoomService;
import acn.intern.appointmentservice.model.Room;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;
    private final RoomMapper mapper;

    @Override
    public List<Room> getAllRooms() {
        List<RoomDAO> roomDAOs = repository.findAll();
        log.info("Found rooms: {}", roomDAOs.size());
        return roomDAOs.stream().map(mapper::roomDAOToRoom).collect(Collectors.toList());
    }

    @Override
    public Optional<Room> getRoomById(Integer id) {
        Optional<RoomDAO> roomDAO = repository.findById(id);
        if (roomDAO.isEmpty()) {
            log.error("Room with id {} not found", id);
            return Optional.empty();
        }
        return Optional.of(mapper.roomDAOToRoom(roomDAO.get()));
    }

    @Override
    public Room postRoom(Room room) {
        RoomDAO newRoomDAO = repository.save(mapper.roomToRoomDAO(room));
        Room newRoom = mapper.roomDAOToRoom(newRoomDAO);
        log.info("Room saved: {}", newRoom);
        return newRoom;
    }

    @Override
    public Room updateRoom(Integer id, Room room) {
        room.setId(id);
        RoomDAO updatedRoomDAO = repository.save(mapper.roomToRoomDAO(room));
        Room updatedRoom = mapper.roomDAOToRoom(updatedRoomDAO);
        log.info("Room updated: {}", updatedRoom);
        return updatedRoom;
    }

    @Override
    public void deleteRoom(Integer id) {
        log.info("Deleting room: {}", id);
        repository.deleteById(id);
    }
}
