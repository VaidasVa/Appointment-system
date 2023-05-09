package acn.intern.bookingservice.business.service.impl;

import acn.intern.bookingservice.business.mappers.BookingMapper;
import acn.intern.bookingservice.business.repository.BookingRepository;
import acn.intern.bookingservice.business.repository.impl.BookingDAO;
import acn.intern.bookingservice.model.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl service;
    @Mock
    private BookingRepository repository;
    @Mock
    private BookingMapper mapper;

    private Booking booking;
    private BookingDAO bookingDAO;
    private List<BookingDAO> bookingDAOList;

    @BeforeEach
    void setup(){
        booking = createBookingInstance(
                UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"),
                "ed7587da-777d-43e4-92b8-075cf117c1bb",
                "cf735961-5a98-4545-905a-9bd2759e48bb",
                LocalDateTime.of(2023, 05, 9, 13, 33, 00),
                LocalDateTime.of(2023, 05, 9, 13, 34, 00),
                 "Descrip."
        );

        bookingDAO = createBookingDAOInstance(
                UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"),
                "ed7587da-777d-43e4-92b8-075cf117c1bb",
                "cf735961-5a98-4545-905a-9bd2759e48bb",
                LocalDateTime.of(2023, 05, 9, 13, 33, 00),
                LocalDateTime.of(2023, 05, 9, 13, 34, 00),
                "Descrip."
        );
        bookingDAOList = createBookingDAOList(bookingDAO);
    }

    @Test
    void getAllBookings_Positive() {
        when(repository.findAll()).thenReturn(bookingDAOList);
        when(mapper.bookingDAOToBooking(bookingDAO)).thenReturn(booking);
        List<Booking> list = service.getAllBookings();

        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"), list.get(0).getId());
        Assertions.assertEquals("Descrip.", list.get(1).getDescription());
        Assertions.assertEquals(LocalDateTime.of(2023, 05, 9, 13, 33, 00), list.get(2).getBookingDate());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAllBookings_Negative_Empty() {
        List<BookingDAO> list = new ArrayList<>();
        when(repository.findAll()).thenReturn(list);
        List<Booking> actualList = service.getAllBookings();
        Assertions.assertTrue(actualList.isEmpty());
    }

    @Test
    void getBookingById_Positive() {
        when(repository.findById(UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08")))
                .thenReturn(Optional.of(bookingDAO));
        when(mapper.bookingDAOToBooking(bookingDAO)).thenReturn(booking);

        Optional<Booking> booking4test = service.getBookingById(
                UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"));
        booking4test.ifPresent(
                found -> assertEquals("Descrip.", found.getDescription()));
    }

    @Test
    void getBookingById_Negative() {
        when(repository.findById(UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08")))
                .thenReturn(Optional.empty());

        Exception ex = assertThrows(ResponseStatusException.class,
                () -> service.getBookingById(
                        UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08")));

        assertThat(ex.getMessage()).isNotNull();
        Assertions.assertTrue(ex.getMessage().contains("404"));
    }

    @Test
    void postBooking_Positive() {
        when(repository.save(bookingDAO)).thenReturn(bookingDAO);
        when(mapper.bookingDAOToBooking(bookingDAO)).thenReturn(booking);
        when(mapper.bookingToBookingDAO(booking)).thenReturn(bookingDAO);

        Booking saved = service.postBooking(booking);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        verify(repository, times(1)).save(bookingDAO);
    }

    @Test
    void postBooking_Negative() {
        Booking bookingEmpty = createBookingInstance(null, null,null, null, null, null);
        BookingDAO bookingDAOEmpty = createBookingDAOInstance(null,null, null, null, null, null);

        when(repository.save(bookingDAOEmpty)).thenReturn(bookingDAOEmpty);
        when(mapper.bookingDAOToBooking(bookingDAOEmpty)).thenReturn(bookingEmpty);
        when(mapper.bookingToBookingDAO(bookingEmpty)).thenReturn(bookingDAOEmpty);

        Booking saved = service.postBooking(bookingEmpty);
        Assertions.assertNotEquals(saved.getId(), UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"));
        assertThat(saved.getId()).isNull();
        verify(repository, times(1)).save(bookingDAOEmpty);
    }

    @Test
    void updateBooking_Positive() {
        BookingDAO existingDAO = new BookingDAO(
                UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"),
                "ed7587da-777d-43e4-92b8-075cf117c1bb",
                "cf735961-5a98-4545-905a-9bd2759e48bb",
                LocalDateTime.of(2023, 05, 9, 13, 33, 00),
                LocalDateTime.of(2023, 05, 9, 13, 34, 00),
                "Descrip."
        );
        BookingDAO updatedDAO = new BookingDAO(
                UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"),
                "ed7587da-777d-43e4-92b8-101010101010",
                "cf735961-5a98-4545-905a-101010101010",
                LocalDateTime.of(2023, 10, 9, 13, 33, 00),
                LocalDateTime.of(2023, 10, 10, 10, 10, 10),
                "Descrip10."
        );

        Booking updated = new Booking(
                UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"),
                "ed7587da-777d-43e4-92b8-101010101010",
                "cf735961-5a98-4545-905a-101010101010",
                LocalDateTime.of(2023, 10, 9, 13, 33, 00),
                LocalDateTime.of(2023, 10, 10, 10, 10, 10),
                "Descrip10."
        );

        when(repository.findById(UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08")))
                .thenReturn(Optional.of(existingDAO));
        when(repository.save(updatedDAO)).thenReturn(updatedDAO);
        when(mapper.bookingDAOToBooking(updatedDAO)).thenReturn(updated);

        Assertions.assertEquals(Optional.of(updated), service.updateBooking(
                UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"), updated));
        verify(repository, times(1)).save(updatedDAO);
    }

    @Test
    void updateBooking_Negative() {
        when(repository.findById(UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08")))
                .thenReturn(Optional.empty());
        Exception ex = assertThrows(ResponseStatusException.class,
                () -> service.updateBooking(UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"), booking));
        assertTrue(ex.getMessage().contains("404"));
    }

    @Test
    void deleteBooking() {
        service.deleteBooking(UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"));
        verify(repository, times(1)).deleteById(UUID.fromString("0edbf21e-a513-4a82-86f6-80109e650a08"));
    }


    private Booking createBookingInstance(UUID id, String appointmentId,
                                         String userId, LocalDateTime bookingDate,
                                         LocalDateTime cancellationDate, String description){
        Booking booking = new Booking();
        booking.setId(id);
        booking.setAppointmentId(appointmentId);
        booking.setUserId(userId);
        booking.setBookingDate(bookingDate);
        booking.setCancellationDate(cancellationDate);
        booking.setDescription(description);
        return booking;
    }

    private BookingDAO createBookingDAOInstance(UUID id, String appointmentId,
                                         String userId, LocalDateTime bookingDate,
                                         LocalDateTime cancellationDate, String description){
        BookingDAO bookingDAO = new BookingDAO();
        booking.setId(id);
        booking.setAppointmentId(appointmentId);
        booking.setUserId(userId);
        booking.setBookingDate(bookingDate);
        booking.setCancellationDate(cancellationDate);
        booking.setDescription(description);
        return bookingDAO;
    }

    private List<BookingDAO> createBookingDAOList(BookingDAO bookingDAO){
        List<BookingDAO> list = new ArrayList<>();
        list.add(bookingDAO);
        list.add(bookingDAO);
        list.add(bookingDAO);
        return list;
    }

}