package acn.intern.bookingservice.web.controller;

import acn.intern.bookingservice.business.service.BookingService;
import acn.intern.bookingservice.model.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    private static final String URL = "/api/v1/booking";
    private static final String URL_ID = "/api/v1/booking/0edbf21e-a513-4a82-86f6-80109e650a08";
    private static final UUID BOOKING_ID = UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08");

    private Booking booking = Booking.builder()
            .id(UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"))
            .appointmentId("ed7587da-777d-43e4-92b8-075cf117c1bb")
            .userId("cf735961-5a98-4545-905a-9bd2759e48bb")
            .bookingDate(LocalDateTime.of(2023, 05, 9, 13, 33, 01))
            .cancellationDate(LocalDateTime.of(2023, 05, 9, 13, 34, 00))
            .description("Descrip.")
            .build();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService service;

    @Test
    void getAllBookings_Positive() throws Exception{
        List<Booking> list = makeTestList();

        when(service.getAllBookings()).thenReturn(list);

        mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$[0].id", is("0edbf21e-a513-4a82-86f6-000000000001")))
                .andExpect(jsonPath("$[1].id", is("0edbf21e-a513-4a82-86f6-000000000002")))
                .andExpect(jsonPath("$[2].id", is("0edbf21e-a513-4a82-86f6-000000000003")));
    }

    @Test
    void getAllBookings_Empty() throws Exception {
        when(service.getAllBookings()).thenReturn(new ArrayList<>());
        mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    void getBookingById_Positive() throws Exception{
        when(service.getBookingById(BOOKING_ID))
                .thenReturn(Optional.of(booking));

        mockMvc.perform(get(URL_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookingDate", is(
                        LocalDateTime.of(2023, 05, 9, 13, 33, 01).toString())));
    }

    @Test
    void getBookingById_NotFound() throws Exception{
        when(service.getBookingById(BOOKING_ID))
                .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(404)));

        mockMvc.perform(get(URL_ID)).andExpect(status().isNotFound());

        verify(service,times(1)).getBookingById(BOOKING_ID);
    }

    @Test
    void postBooking_Positive() throws Exception{
        when(service.postBooking(booking)).thenReturn(booking);

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isCreated());
    }

    @Test
    void postBookings_Negative() throws Exception{
        Booking empty = Booking.builder().description("").build();

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empty)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBooking_Positive() throws Exception{
        when(service.updateBooking(BOOKING_ID, booking))
                .thenReturn(Optional.of(booking));

        mockMvc.perform(MockMvcRequestBuilders.put(URL_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isNoContent());

        verify(service).updateBooking(BOOKING_ID, booking);
    }

    @Test
    void updateBooking_Negative() throws Exception{
        Booking empty = Booking.builder().description("").build();

        when(service.updateBooking(BOOKING_ID, empty))
                .thenThrow(new ResponseStatusException(BAD_REQUEST));

        mockMvc.perform(MockMvcRequestBuilders.put(URL_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empty)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void updateBooking_NotFoundForUpdate() throws Exception{
        when(service.updateBooking(BOOKING_ID, booking))
                .thenThrow(new ResponseStatusException(NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders.put(URL_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBooking_Positive() throws Exception{
        given(service.getBookingById(BOOKING_ID)).willReturn(Optional.ofNullable(booking));
        mockMvc.perform(delete(URL_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBooking_Negative() throws Exception{
        doThrow(new ResponseStatusException(NOT_FOUND))
                .when(service).deleteBooking(BOOKING_ID);
        mockMvc.perform(delete(URL_ID))
                .andExpect(status().isNotFound());
    }

    private List<Booking> makeTestList(){
        List<Booking> list = new ArrayList<>();

        Booking booking1 = Booking.builder()
                .id(UUID.fromString("0edbf21e-a513-4a82-86f6-000000000001"))
                .appointmentId("ed7587da-777d-43e4-92b8-075cf117c1bb")
                .userId("cf735961-5a98-4545-905a-9bd2759e48bb")
                .bookingDate(LocalDateTime.of(2023, 1, 2, 3, 4, 5))
                .cancellationDate(LocalDateTime.of(2023, 2, 3, 4, 5, 6))
                .description("Descrip.")
                .build();

        Booking booking2 = Booking.builder()
                .id(UUID.fromString("0edbf21e-a513-4a82-86f6-000000000002"))
                .appointmentId("ed7587da-777d-43e4-92b8-075cf117c1bb")
                .userId("cf735961-5a98-4545-905a-9bd2759e48bb")
                .bookingDate(LocalDateTime.of(2023, 3, 4, 5, 6, 7))
                .cancellationDate(LocalDateTime.of(2023, 4, 5, 6, 7, 8))
                .description("Descrip.")
                .build();

        Booking booking3 = Booking.builder()
                .id(UUID.fromString("0edbf21e-a513-4a82-86f6-000000000003"))
                .appointmentId("ed7587da-777d-43e4-92b8-075cf117c1bb")
                .userId("cf735961-5a98-4545-905a-9bd2759e48bb")
                .bookingDate(LocalDateTime.of(2023, 8, 9, 10, 11, 12))
                .cancellationDate(LocalDateTime.of(2023, 9, 10, 11, 12, 13))
                .description("Descrip.")
                .build();

        list.add(booking1);
        list.add(booking2);
        list.add(booking3);

        return list;
    }

}