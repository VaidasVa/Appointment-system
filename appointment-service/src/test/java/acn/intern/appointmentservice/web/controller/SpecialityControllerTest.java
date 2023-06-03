package acn.intern.appointmentservice.web.controller;

import acn.intern.appointmentservice.business.service.SpecialityService;
import acn.intern.appointmentservice.model.Speciality;
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
class SpecialityControllerTest {
    public static String URL = "/api/v1/speciality";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpecialityController controller;

    @MockBean
    private SpecialityService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllSpecialities() throws Exception {
        List<Speciality> specialityList = List.of(createSpeciality());
        when(service.getAllSpecialities()).thenReturn(specialityList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Family doctor")));

        verify(service, times(1)).getAllSpecialities();
    }

    @Test
    void getAllSpecialitiesEmpty() throws Exception {
        List<Speciality> specialityList = new ArrayList<>();
        when(service.getAllSpecialities()).thenReturn(specialityList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(service, times(1)).getAllSpecialities();
    }

    @Test
    void getSpecialityById() throws Exception {
        when(service.getSpecialityById(1))
                .thenReturn(Optional.of(createSpeciality()));

        mockMvc.perform(get(URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Family doctor")));
    }

    @Test
    void getSpecialityByIdNotFound() throws Exception {
        when(service.getSpecialityById(1))
                .thenReturn(Optional.empty());

        mockMvc.perform(get(URL + "/1"))
                .andExpect(status().isNotFound());
        verify(service, times(1)).getSpecialityById(any());
    }

    @Test
    void postSpeciality() throws Exception {
        Speciality specialityWithoutId = createSpeciality();
        specialityWithoutId.setId(null);
        when(service.postSpeciality(specialityWithoutId)).thenReturn(createSpeciality());

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(specialityWithoutId)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Family doctor")));

        verify(service, times(1)).postSpeciality(any());
    }

    @Test
    void postSpecialityInvalidInput() throws Exception {
        Speciality SpecialityWithoutId = createSpeciality();
        SpecialityWithoutId.setId(null);
        SpecialityWithoutId.setName(null);

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SpecialityWithoutId)))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).postSpeciality(any());
    }

    @Test
    void putSpeciality() throws Exception {
        Speciality oldSpeciality = createSpeciality();
        Speciality newSpeciality = createSpeciality();
        newSpeciality.setName("General practitioner");
        when(service.getSpecialityById(1)).thenReturn(Optional.of(oldSpeciality));
        when(service.updateSpeciality(1, newSpeciality)).thenReturn(newSpeciality);
        mockMvc.perform(put(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSpeciality)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("General practitioner")));

        verify(service, times(1)).updateSpeciality(any(), any());
    }

    @Test
    void putSpecialityInvalidInput() throws Exception {
        Speciality newSpeciality = createSpeciality();
        newSpeciality.setName(null);

        mockMvc.perform(put(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSpeciality)))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).updateSpeciality(any(), any());
    }

    @Test
    void putSpecialityNotFound() throws Exception {
        Speciality newSpeciality = createSpeciality();
        when(service.getSpecialityById(1))
                .thenReturn(Optional.empty());

        mockMvc.perform(put(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSpeciality)))
                .andExpect(status().isNotFound());

        verify(service, times(0)).updateSpeciality(any(), any());
    }

    @Test
    void deleteSpeciality() throws Exception {
        Speciality Speciality = createSpeciality();
        when(service.getSpecialityById(1))
                .thenReturn(Optional.of(Speciality));

        mockMvc.perform(delete(URL + "/1")).andExpect(status().isNoContent());

        verify(service, times(1)).deleteSpeciality(any());
    }

    @Test
    void deleteSpecialityNotFound() throws Exception {
        when(service.getSpecialityById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete(URL + "/1")).andExpect(status().isNotFound());
        verify(service, times(0)).deleteSpeciality(any());
    }

    private Speciality createSpeciality() {
        return Speciality.builder()
                .id(1)
                .name("Family doctor")
                .build();
    }
}