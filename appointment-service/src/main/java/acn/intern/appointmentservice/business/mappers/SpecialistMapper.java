package acn.intern.appointmentservice.business.mappers;

import acn.intern.appointmentservice.business.repository.impl.SpecialistDAO;
import acn.intern.appointmentservice.model.Specialist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {SpecialityMapper.class})
public interface SpecialistMapper {

    SpecialistMapper INSTANCE = Mappers.getMapper(SpecialistMapper.class);

    Specialist specialistDAOToSpecialist(SpecialistDAO specialistDAO);
    SpecialistDAO specialistToSpecialistDAO(Specialist specialist);
}
