package acn.intern.appointmentservice.business.mappers;

import acn.intern.appointmentservice.business.repository.impl.SpecialityDAO;
import acn.intern.appointmentservice.model.Speciality;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialityMapper {

    Speciality specialityDAOToSpeciality(SpecialityDAO specialityDAO);
    SpecialityDAO specialityToSpecialityDAO(Speciality speciality);
}
