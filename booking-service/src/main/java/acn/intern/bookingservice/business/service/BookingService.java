package acn.intern.bookingservice.business.service;

import acn.intern.bookingservice.model.Booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingService {

    List<Booking> getAllBookings();

    Optional<Booking> getBookingById(UUID id);

    Booking postBooking(Booking booking);

    Optional<Booking> updateBooking(UUID id, Booking booking);

    void deleteBooking(UUID id);

}
