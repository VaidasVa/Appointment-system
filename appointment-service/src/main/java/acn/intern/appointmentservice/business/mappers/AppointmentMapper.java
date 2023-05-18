package acn.intern.appointmentservice.business.mappers;

import acn.intern.appointmentservice.business.repository.impl.AppointmentDAO;
import acn.intern.appointmentservice.model.Appointment;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DateTimeMapper.class)
public interface AppointmentMapper {

    Appointment appointmentDAOToAppointment(AppointmentDAO appointmentDAO);
    AppointmentDAO appointmentToAppointmentDAO(Appointment appointment);
}
