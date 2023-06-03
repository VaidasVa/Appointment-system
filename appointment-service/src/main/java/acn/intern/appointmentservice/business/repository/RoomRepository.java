package acn.intern.appointmentservice.business.repository;

import acn.intern.appointmentservice.business.repository.impl.RoomDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomDAO, Integer> {
}
