package acn.intern.appointmentservice.business.repository;

import acn.intern.appointmentservice.business.repository.impl.AppointmentDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<AppointmentDAO, UUID> {
}
