package acn.intern.bookingservice.business.mappers;

import acn.intern.bookingservice.business.repository.impl.BookingDAO;
import acn.intern.bookingservice.model.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking bookingDAOToBooking(BookingDAO bookingDAO);
    BookingDAO bookingToBookingDAO(Booking booking);
}
