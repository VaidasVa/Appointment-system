package acn.intern.bookingservice.business.repository;

import acn.intern.bookingservice.business.repository.impl.BookingDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookingDAO, UUID> {
}
