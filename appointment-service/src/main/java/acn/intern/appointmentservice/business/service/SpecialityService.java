package acn.intern.appointmentservice.business.service;

import acn.intern.appointmentservice.model.Speciality;

import java.util.List;
import java.util.Optional;

public interface SpecialityService {
    List<Speciality> getAllSpecialities();
    Optional<Speciality> getSpecialityById(Integer id);

    Speciality postSpeciality(Speciality Speciality);

    Speciality updateSpeciality(Integer id, Speciality Speciality);

    void deleteSpeciality(Integer id);
}
