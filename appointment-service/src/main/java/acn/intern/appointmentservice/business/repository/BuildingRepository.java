package acn.intern.appointmentservice.business.repository;

import acn.intern.appointmentservice.business.repository.impl.BuildingDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<BuildingDAO, Integer> {
}
