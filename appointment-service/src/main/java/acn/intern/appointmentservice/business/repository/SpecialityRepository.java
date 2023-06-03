package acn.intern.appointmentservice.business.repository;

import acn.intern.appointmentservice.business.repository.impl.SpecialityDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialityRepository extends JpaRepository<SpecialityDAO, Integer> {
}
