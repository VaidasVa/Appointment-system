package acn.intern.appointmentservice.business.service.impl;

import acn.intern.appointmentservice.business.mappers.RoomMapper;
import acn.intern.appointmentservice.business.repository.RoomRepository;
import acn.intern.appointmentservice.business.repository.impl.BuildingDAO;
import acn.intern.appointmentservice.business.repository.impl.RoomDAO;
import acn.intern.appointmentservice.model.Building;
import acn.intern.appointmentservice.model.Room;
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
class RoomServiceImplTest {

    private Room room;

    private RoomDAO roomDAO;

    @Mock
    RoomRepository repository;

    @Mock
    RoomMapper mapper;

    @InjectMocks
    RoomServiceImpl service;

    @BeforeEach
    void initializeValues() {
        room = createRoom();
        roomDAO = createRoomDAO();
    }

    @Test
    void getAllRooms() {
        when(repository.findAll()).thenReturn(List.of(roomDAO)).thenReturn(new ArrayList<>());
        when(mapper.roomDAOToRoom(roomDAO)).thenReturn(room);
        assertEquals(List.of(room), service.getAllRooms());
        assertEquals(0, service.getAllRooms().size());
        verify(repository, times(2)).findAll();
    }

    @Test
    void getRoomById() {
        when(repository.findById(1))
                .thenReturn(Optional.ofNullable(roomDAO))
                .thenReturn(Optional.empty());
        when(mapper.roomDAOToRoom(roomDAO)).thenReturn(room);
        assertEquals(Optional.of(room), service.getRoomById(1));
        assertEquals(Optional.empty(), service.getRoomById(1));
        verify(repository, times(2)).findById(any());
    }

    @Test
    void postRoom() {
        Room roomWithoutId = createRoom();
        roomWithoutId.setId(null);
        RoomDAO roomDAOWithoutId = createRoomDAO();
        roomDAOWithoutId.setId(null);

        when(repository.save(roomDAOWithoutId)).thenReturn(roomDAO);
        when(mapper.roomToRoomDAO(roomWithoutId)).thenReturn(roomDAOWithoutId);
        when(mapper.roomDAOToRoom(roomDAO)).thenReturn(room);

        assertEquals(room, service.postRoom(roomWithoutId));
        verify(repository, times(1)).save(roomDAOWithoutId);
    }

    @Test
    void updateRoom() {
        when(repository.save(roomDAO)).thenReturn(roomDAO);
        when(mapper.roomToRoomDAO(room)).thenReturn(roomDAO);
        when(mapper.roomDAOToRoom(roomDAO)).thenReturn(room);

        assertEquals(room, service.updateRoom(1, room));
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteRoom() {
        assertDoesNotThrow(() -> service.deleteRoom(1));
        verify(repository, times(1)).deleteById(any());
    }


    private Room createRoom() {
        return Room.builder()
                .id(1)
                .name("Exam Room 1")
                .building(Building.builder()
                                    .id(1)
                                    .name("Building One")
                                    .address("Broad st. 3")
                                    .city("Vilnius")
                                    .postCode("000123")
                                    .build())
                .build();
    }

    private RoomDAO createRoomDAO() {
        return RoomDAO.builder()
                .id(1)
                .name("Exam Room 1")
                .building(BuildingDAO.builder()
                                        .id(1)
                                        .name("Building One")
                                        .address("Broad st. 3")
                                        .city("Vilnius")
                                        .postCode("000123")
                                        .build())
                .build();
    }
}