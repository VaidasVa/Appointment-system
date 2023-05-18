package acn.intern.appointmentservice.web.controller;

import acn.intern.appointmentservice.business.service.AppointmentService;
import acn.intern.appointmentservice.model.Appointment;

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
class AppointmentControllerTest {

    public static String URL = "/api/v1/appointment";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppointmentController controller;

    @MockBean
    private AppointmentService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllAppointments() throws Exception {
        List<Appointment> appointmentList = List.of(createAppointment());
        when(service.getAllAppointments()).thenReturn(appointmentList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("c700f297-5db1-4a03-a4f0-e9e174510696")))
                .andExpect(jsonPath("$[0].start", is("2023-01-01 14:00:00")))
                .andExpect(jsonPath("$[0].end", is("2023-01-01 15:00:00")))
                .andExpect(jsonPath("$[0].roomId", is(123)))
                .andExpect(jsonPath("$[0].price", is(15.00)))
                .andExpect(jsonPath("$[0].specialistId", is("d6326e2f-067f-4d61-b6d0-14e9278172f8")));

        verify(service, times(1)).getAllAppointments();
    }

    @Test
    void getAllAppointmentsEmpty() throws Exception {
        List<Appointment> appointmentList = new ArrayList<>();
        when(service.getAllAppointments()).thenReturn(appointmentList);

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(service, times(1)).getAllAppointments();
    }

    @Test
    void getAppointmentById() throws Exception {
        when(service.getAppointmentById(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696")))
                .thenReturn(Optional.of(createAppointment()));

        mockMvc.perform(get(URL + "/c700f297-5db1-4a03-a4f0-e9e174510696"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("c700f297-5db1-4a03-a4f0-e9e174510696")))
                .andExpect(jsonPath("$.start", is("2023-01-01 14:00:00")))
                .andExpect(jsonPath("$.end", is("2023-01-01 15:00:00")))
                .andExpect(jsonPath("$.roomId", is(123)))
                .andExpect(jsonPath("$.price", is(15.00)))
                .andExpect(jsonPath("$.specialistId", is("d6326e2f-067f-4d61-b6d0-14e9278172f8")));
        verify(service, times(1)).getAppointmentById(any());
    }

    @Test
    void getAppointmentByIdNotFound() throws Exception {
        when(service.getAppointmentById(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696")))
                .thenReturn(Optional.empty());

        mockMvc.perform(get(URL + "/c700f297-5db1-4a03-a4f0-e9e174510696"))
                .andExpect(status().isNotFound());
        verify(service, times(1)).getAppointmentById(any());
    }

    @Test
    void postAppointment() throws Exception {
        Appointment appointmentWithoutId = createAppointment();
        appointmentWithoutId.setId(null);
        when(service.postAppointment(appointmentWithoutId)).thenReturn(createAppointment());

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(appointmentWithoutId)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("c700f297-5db1-4a03-a4f0-e9e174510696")))
                .andExpect(jsonPath("$.start", is("2023-01-01 14:00:00")))
                .andExpect(jsonPath("$.end", is("2023-01-01 15:00:00")))
                .andExpect(jsonPath("$.roomId", is(123)))
                .andExpect(jsonPath("$.price", is(15.00)))
                .andExpect(jsonPath("$.specialistId", is("d6326e2f-067f-4d61-b6d0-14e9278172f8")));

        verify(service, times(1)).postAppointment(any());
    }

    @Test
    void postAppointmentInvalidInput() throws Exception {
        Appointment appointmentWithoutId = createAppointment();
        appointmentWithoutId.setId(null);
        appointmentWithoutId.setSpecialistId(null);

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(appointmentWithoutId)))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).postAppointment(any());
    }

    @Test
    void putAppointment() throws Exception {
        Appointment oldAppointment = createAppointment();
        Appointment newAppointment = createAppointment();
        newAppointment.setPrice(30.00);
        when(service.getAppointmentById(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696"))).thenReturn(Optional.of(oldAppointment));
        when(service.updateAppointment(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696"), newAppointment)).thenReturn(newAppointment);
        mockMvc.perform(put(URL + "/c700f297-5db1-4a03-a4f0-e9e174510696").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newAppointment)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("c700f297-5db1-4a03-a4f0-e9e174510696")))
                .andExpect(jsonPath("$.start", is("2023-01-01 14:00:00")))
                .andExpect(jsonPath("$.end", is("2023-01-01 15:00:00")))
                .andExpect(jsonPath("$.roomId", is(123)))
                .andExpect(jsonPath("$.price", is(30.00)))
                .andExpect(jsonPath("$.specialistId", is("d6326e2f-067f-4d61-b6d0-14e9278172f8")));

        verify(service, times(1)).updateAppointment(any(), any());
    }

    @Test
    void putAppointmentInvalidInput() throws Exception {
        Appointment newAppointment = createAppointment();
        newAppointment.setEnd(null);

        mockMvc.perform(put(URL + "/c700f297-5db1-4a03-a4f0-e9e174510696")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAppointment)))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).updateAppointment(any(), any());
    }

    @Test
    void putAppointmentNotFound() throws Exception {
        Appointment newAppointment = createAppointment();
        when(service.getAppointmentById(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696")))
                .thenReturn(Optional.empty());

        mockMvc.perform(put(URL + "/c700f297-5db1-4a03-a4f0-e9e174510696")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAppointment)))
                .andExpect(status().isNotFound());

        verify(service, times(0)).updateAppointment(any(), any());
    }

    @Test
    void deleteAppointment() throws Exception {
        Appointment appointment = createAppointment();
        when(service.getAppointmentById(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696")))
                .thenReturn(Optional.of(appointment));

        mockMvc.perform(delete(URL + "/c700f297-5db1-4a03-a4f0-e9e174510696")).andExpect(status().isNoContent());

        verify(service, times(1)).deleteAppointment(any());
    }

    @Test
    void deleteAppointmentNotFound() throws Exception {
        when(service.getAppointmentById(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696"))).thenReturn(Optional.empty());

        mockMvc.perform(delete(URL + "/c700f297-5db1-4a03-a4f0-e9e174510696")).andExpect(status().isNotFound());
        verify(service, times(0)).deleteAppointment(any());
    }

    private Appointment createAppointment() {
        return Appointment.builder().id(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696"))
                .start("2023-01-01 14:00:00")
                .end("2023-01-01 15:00:00")
                .specialistId(UUID.fromString("d6326e2f-067f-4d61-b6d0-14e9278172f8"))
                .roomId(123)
                .price(15.00)
                .build();
    }
}