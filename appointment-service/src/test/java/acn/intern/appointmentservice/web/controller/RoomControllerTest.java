package acn.intern.appointmentservice.web.controller;

import acn.intern.appointmentservice.business.service.RoomService;
import acn.intern.appointmentservice.model.Building;
import acn.intern.appointmentservice.model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class RoomControllerTest {
    public static String URL = "/api/v1/room";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomController controller;

    @MockBean
    private RoomService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllRooms() throws Exception {
        List<Room> roomList = List.of(createRoom());
        when(service.getAllRooms()).thenReturn(roomList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Exam Room 1")))
                .andExpect(jsonPath("$[0].building.address", is("Broad st. 3")))
                .andExpect(jsonPath("$[0].building.city", is("Vilnius")));

        verify(service, times(1)).getAllRooms();
    }

    @Test
    void getAllRoomsEmpty() throws Exception {
        List<Room> roomList = new ArrayList<>();
        when(service.getAllRooms()).thenReturn(roomList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(service, times(1)).getAllRooms();
    }

    @Test
    void getRoomById() throws Exception {
        when(service.getRoomById(1))
                .thenReturn(Optional.of(createRoom()));

        mockMvc.perform(get(URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Exam Room 1")))
                .andExpect(jsonPath("$.building.address", is("Broad st. 3")))
                .andExpect(jsonPath("$.building.city", is("Vilnius")));
        verify(service, times(1)).getRoomById(any());
    }

    @Test
    void getRoomByIdNotFound() throws Exception {
        when(service.getRoomById(1))
                .thenReturn(Optional.empty());

        mockMvc.perform(get(URL + "/1"))
                .andExpect(status().isNotFound());
        verify(service, times(1)).getRoomById(any());
    }

    @Test
    void postRoom() throws Exception {
        Room roomWithoutId = createRoom();
        roomWithoutId.setId(null);
        when(service.postRoom(roomWithoutId)).thenReturn(createRoom());

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(roomWithoutId)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Exam Room 1")))
                .andExpect(jsonPath("$.building.address", is("Broad st. 3")))
                .andExpect(jsonPath("$.building.city", is("Vilnius")));

        verify(service, times(1)).postRoom(any());
    }

    @Test
    void postRoomInvalidInput() throws Exception {
        Room roomWithoutId = createRoom();
        roomWithoutId.setId(null);
        roomWithoutId.setBuilding(null);

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomWithoutId)))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).postRoom(any());
    }

    @Test
    void putRoom() throws Exception {
        Room oldRoom = createRoom();
        Room newRoom = createRoom();
        newRoom.setName("New Room");
        when(service.getRoomById(1)).thenReturn(Optional.of(oldRoom));
        when(service.updateRoom(1, newRoom)).thenReturn(newRoom);
        mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newRoom)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Room")))
                .andExpect(jsonPath("$.building.address", is("Broad st. 3")))
                .andExpect(jsonPath("$.building.city", is("Vilnius")));

        verify(service, times(1)).updateRoom(any(), any());
    }

    @Test
    void putRoomInvalidInput() throws Exception {
        Room newRoom = createRoom();
        newRoom.setBuilding(null);

        mockMvc.perform(put(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRoom)))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).updateRoom(any(), any());
    }

    @Test
    void putRoomNotFound() throws Exception {
        Room newRoom = createRoom();
        when(service.getRoomById(1))
                .thenReturn(Optional.empty());

        mockMvc.perform(put(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRoom)))
                .andExpect(status().isNotFound());

        verify(service, times(0)).updateRoom(any(), any());
    }

    @Test
    void deleteRoom() throws Exception {
        Room room = createRoom();
        when(service.getRoomById(1))
                .thenReturn(Optional.of(room));

        mockMvc.perform(delete(URL + "/1")).andExpect(status().isNoContent());

        verify(service, times(1)).deleteRoom(any());
    }

    @Test
    void deleteRoomNotFound() throws Exception {
        when(service.getRoomById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete(URL + "/1")).andExpect(status().isNotFound());
        verify(service, times(0)).deleteRoom(any());
    }

    private Room createRoom() {
        return Room.builder()
                .id(1)
                .name("Exam Room 1")
                .building(Building.builder()
                        .id(1)
                        .name("Room One")
                        .address("Broad st. 3")
                        .city("Vilnius")
                        .postCode("000123")
                        .build())
                .build();
    }
}