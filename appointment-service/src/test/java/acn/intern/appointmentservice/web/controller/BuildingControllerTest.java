package acn.intern.appointmentservice.web.controller;

import acn.intern.appointmentservice.business.service.BuildingService;
import acn.intern.appointmentservice.model.Building;
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
class BuildingControllerTest {

    public static String URL = "/api/v1/building";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BuildingController controller;

    @MockBean
    private BuildingService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBuildings() throws Exception {
        List<Building> buildingList = List.of(createBuilding());
        when(service.getAllBuildings()).thenReturn(buildingList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Building One")))
                .andExpect(jsonPath("$[0].address", is("Broad st. 3")))
                .andExpect(jsonPath("$[0].city", is("Vilnius")))
                .andExpect(jsonPath("$[0].postCode", is("000123")));

        verify(service, times(1)).getAllBuildings();
    }

    @Test
    void getAllBuildingsEmpty() throws Exception {
        List<Building> buildingList = new ArrayList<>();
        when(service.getAllBuildings()).thenReturn(buildingList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(service, times(1)).getAllBuildings();
    }

    @Test
    void getBuildingById() throws Exception {
        when(service.getBuildingById(1))
                .thenReturn(Optional.of(createBuilding()));

        mockMvc.perform(get(URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Building One")))
                .andExpect(jsonPath("$.address", is("Broad st. 3")))
                .andExpect(jsonPath("$.city", is("Vilnius")))
                .andExpect(jsonPath("$.postCode", is("000123")));
        verify(service, times(1)).getBuildingById(any());
    }

    @Test
    void getBuildingByIdNotFound() throws Exception {
        when(service.getBuildingById(1))
                .thenReturn(Optional.empty());

        mockMvc.perform(get(URL + "/1"))
                .andExpect(status().isNotFound());
        verify(service, times(1)).getBuildingById(any());
    }

    @Test
    void postBuilding() throws Exception {
        Building buildingWithoutId = createBuilding();
        buildingWithoutId.setId(null);
        when(service.postBuilding(buildingWithoutId)).thenReturn(createBuilding());

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(buildingWithoutId)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Building One")))
                .andExpect(jsonPath("$.address", is("Broad st. 3")))
                .andExpect(jsonPath("$.city", is("Vilnius")))
                .andExpect(jsonPath("$.postCode", is("000123")));

        verify(service, times(1)).postBuilding(any());
    }

    @Test
    void postBuildingInvalidInput() throws Exception {
        Building buildingWithoutId = createBuilding();
        buildingWithoutId.setId(null);
        buildingWithoutId.setAddress(null);

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(buildingWithoutId)))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).postBuilding(any());
    }

    @Test
    void putBuilding() throws Exception {
        Building oldBuilding = createBuilding();
        Building newBuilding = createBuilding();
        newBuilding.setName("New Building");
        when(service.getBuildingById(1)).thenReturn(Optional.of(oldBuilding));
        when(service.updateBuilding(1, newBuilding)).thenReturn(newBuilding);
        mockMvc.perform(put(URL + "/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newBuilding)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Building")))
                .andExpect(jsonPath("$.address", is("Broad st. 3")))
                .andExpect(jsonPath("$.city", is("Vilnius")))
                .andExpect(jsonPath("$.postCode", is("000123")));

        verify(service, times(1)).updateBuilding(any(), any());
    }

    @Test
    void putBuildingInvalidInput() throws Exception {
        Building newBuilding = createBuilding();
        newBuilding.setAddress(null);

        mockMvc.perform(put(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBuilding)))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).updateBuilding(any(), any());
    }

    @Test
    void putBuildingNotFound() throws Exception {
        Building newBuilding = createBuilding();
        when(service.getBuildingById(1))
                .thenReturn(Optional.empty());

        mockMvc.perform(put(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBuilding)))
                .andExpect(status().isNotFound());

        verify(service, times(0)).updateBuilding(any(), any());
    }

    @Test
    void deleteBuilding() throws Exception {
        Building Building = createBuilding();
        when(service.getBuildingById(1))
                .thenReturn(Optional.of(Building));

        mockMvc.perform(delete(URL + "/1")).andExpect(status().isNoContent());

        verify(service, times(1)).deleteBuilding(any());
    }

    @Test
    void deleteBuildingNotFound() throws Exception {
        when(service.getBuildingById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete(URL + "/1")).andExpect(status().isNotFound());
        verify(service, times(0)).deleteBuilding(any());
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
}