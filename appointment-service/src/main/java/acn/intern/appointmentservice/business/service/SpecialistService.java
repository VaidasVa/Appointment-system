package acn.intern.appointmentservice.business.service;

import acn.intern.appointmentservice.model.Specialist;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpecialistService {

    List<Specialist> getAllSpecialists();

    Optional<Specialist> getSpecialistById(UUID id);

    Specialist postSpecialist(Specialist Specialist);

    Specialist updateSpecialist(UUID id, Specialist Specialist);

    void deleteSpecialist(UUID id);
}
