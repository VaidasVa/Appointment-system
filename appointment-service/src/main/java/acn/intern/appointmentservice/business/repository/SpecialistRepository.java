package acn.intern.appointmentservice.business.repository;

import acn.intern.appointmentservice.business.repository.impl.SpecialistDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpecialistRepository extends JpaRepository<SpecialistDAO, UUID> {
}
