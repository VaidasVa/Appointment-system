package acn.intern.appointmentservice.web.controller;

import acn.intern.appointmentservice.business.service.SpecialistService;
import acn.intern.appointmentservice.model.Specialist;
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
import java.util.UUID;

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
class SpecialistControllerTest {

    public static String URL = "/api/v1/specialist";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpecialistController controller;

    @MockBean
    private SpecialistService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllSpecialists() throws Exception {
        List<Specialist> specialistList = List.of(createSpecialist());
        when(service.getAllSpecialists()).thenReturn(specialistList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")))
                .andExpect(jsonPath("$[0].userId", is("1e5cf2f4-110f-4d59-9af5-0974afcd3b26")))
                .andExpect(jsonPath("$[0].speciality.name", is("Family doctor")));

        verify(service, times(1)).getAllSpecialists();
    }

    @Test
    void getAllSpecialistsEmpty() throws Exception {
        List<Specialist> SpecialistList = new ArrayList<>();
        when(service.getAllSpecialists()).thenReturn(SpecialistList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(service, times(1)).getAllSpecialists();
    }

    @Test
    void getSpecialistById() throws Exception {
        when(service.getSpecialistById(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")))
                .thenReturn(Optional.of(createSpecialist()));

        mockMvc.perform(get(URL + "/5d8bc9bc-bbb9-48f1-8616-258059bf71ec"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")))
                .andExpect(jsonPath("$.userId", is("1e5cf2f4-110f-4d59-9af5-0974afcd3b26")))
                .andExpect(jsonPath("$.speciality.name", is("Family doctor")));
        verify(service, times(1)).getSpecialistById(any());
    }

    @Test
    void getSpecialistByIdNotFound() throws Exception {
        when(service.getSpecialistById(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")))
                .thenReturn(Optional.empty());

        mockMvc.perform(get(URL + "/5d8bc9bc-bbb9-48f1-8616-258059bf71ec"))
                .andExpect(status().isNotFound());
        verify(service, times(1)).getSpecialistById(any());
    }

    @Test
    void postSpecialist() throws Exception {
        Specialist specialistWithoutId = createSpecialist();
        specialistWithoutId.setId(null);
        when(service.postSpecialist(specialistWithoutId)).thenReturn(createSpecialist());

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(specialistWithoutId)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")))
                .andExpect(jsonPath("$.userId", is("1e5cf2f4-110f-4d59-9af5-0974afcd3b26")))
                .andExpect(jsonPath("$.speciality.name", is("Family doctor")));

        verify(service, times(1)).postSpecialist(any());
    }

    @Test
    void postSpecialistInvalidInput() throws Exception {
        Specialist specialistWithoutId = createSpecialist();
        specialistWithoutId.setId(null);
        specialistWithoutId.setSpeciality(null);

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(specialistWithoutId)))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).postSpecialist(any());
    }

    @Test
    void putSpecialist() throws Exception {
        Specialist oldSpecialist = createSpecialist();
        Specialist newSpecialist = createSpecialist();
        newSpecialist.setSpeciality(Speciality.builder().id(2).name("Dentist").build());
        when(service.getSpecialistById(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec"))).thenReturn(Optional.of(oldSpecialist));
        when(service.updateSpecialist(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec"), newSpecialist)).thenReturn(newSpecialist);
        mockMvc.perform(put(URL + "/5d8bc9bc-bbb9-48f1-8616-258059bf71ec")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSpecialist)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")))
                .andExpect(jsonPath("$.userId", is("1e5cf2f4-110f-4d59-9af5-0974afcd3b26")))
                .andExpect(jsonPath("$.speciality.name", is("Dentist")));

        verify(service, times(1)).updateSpecialist(any(), any());
    }

    @Test
    void putSpecialistInvalidInput() throws Exception {
        Specialist newSpecialist = createSpecialist();
        newSpecialist.setSpeciality(null);

        mockMvc.perform(put(URL + "/5d8bc9bc-bbb9-48f1-8616-258059bf71ec")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSpecialist)))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).updateSpecialist(any(), any());
    }

    @Test
    void putSpecialistNotFound() throws Exception {
        Specialist newSpecialist = createSpecialist();
        when(service.getSpecialistById(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")))
                .thenReturn(Optional.empty());

        mockMvc.perform(put(URL + "/5d8bc9bc-bbb9-48f1-8616-258059bf71ec")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSpecialist)))
                .andExpect(status().isNotFound());

        verify(service, times(0)).updateSpecialist(any(), any());
    }

    @Test
    void deleteSpecialist() throws Exception {
        Specialist specialist = createSpecialist();
        when(service.getSpecialistById(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")))
                .thenReturn(Optional.of(specialist));

        mockMvc.perform(delete(URL + "/5d8bc9bc-bbb9-48f1-8616-258059bf71ec"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteSpecialist(any());
    }

    @Test
    void deleteSpecialistNotFound() throws Exception {
        when(service.getSpecialistById(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")))
                .thenReturn(Optional.empty());

        mockMvc.perform(delete(URL + "/5d8bc9bc-bbb9-48f1-8616-258059bf71ec"))
                .andExpect(status().isNotFound());
        verify(service, times(0)).deleteSpecialist(any());
    }

    private Specialist createSpecialist() {
        return Specialist.builder()
                .id(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec"))
                .userId(UUID.fromString("1e5cf2f4-110f-4d59-9af5-0974afcd3b26"))
                .speciality(Speciality.builder()
                        .id(1)
                        .name("Family doctor")
                        .build())
                .build();
    }
}