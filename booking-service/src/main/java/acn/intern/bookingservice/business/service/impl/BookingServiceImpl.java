package acn.intern.bookingservice.business.service.impl;

import acn.intern.bookingservice.business.mappers.BookingMapper;
import acn.intern.bookingservice.business.repository.BookingRepository;
import acn.intern.bookingservice.business.service.BookingService;
import acn.intern.bookingservice.model.Booking;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;
    private final BookingMapper mapper;

    @Override
    public List<Booking> getAllBookings() {
        return repository.findAll()
                .stream()
                .map(mapper::bookingDAOToBooking)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Booking> getBookingById(UUID id) {
        return Optional.ofNullable(repository.findById(id)
                .map(mapper::bookingDAOToBooking)
                .orElseThrow(
                        () -> new ResponseStatusException(NOT_FOUND)));
    }

    @Override
    public Booking postBooking(Booking appointment) {
        return mapper.bookingDAOToBooking(repository
                .save(mapper.bookingToBookingDAO(appointment)));
    }

    @Override
    public Optional<Booking> updateBooking(UUID id, Booking booking) {

        AtomicReference<Optional<Booking>> atomicReference = new AtomicReference<>();

        repository.findById(id).ifPresentOrElse(
                found -> {
                    if (booking.getDescription().isEmpty() || booking.getBookingDate().toString().isEmpty()) {
                        throw new ResponseStatusException(BAD_REQUEST, "Check and fill in mandatory fields. ");
                    } else {
                        found.setAppointmentId(booking.getAppointmentId());
                        found.setUserId(booking.getUserId());
                        found.setBookingDate(booking.getBookingDate());
                        found.setCancellationDate(booking.getCancellationDate());
                        found.setDescription(booking.getDescription());
                        atomicReference.set(Optional.of(
                                mapper.bookingDAOToBooking(repository.save(found))));
                    }
                },
                () -> {
                    throw new ResponseStatusException(NOT_FOUND,
                            "Booking with this ID could not be found for update.");
                });
        return atomicReference.get();
    }

    @Override
    public void deleteBooking(UUID id) {
        repository.deleteById(id);

    }
}
