package acn.intern.appointmentservice.business.service;

import acn.intern.appointmentservice.model.Appointment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentService {

    List<Appointment> getAllAppointments();

    Optional<Appointment> getAppointmentById(UUID id);

    Appointment postAppointment(Appointment appointment);

    Appointment updateAppointment(UUID id, Appointment appointment);

    void deleteAppointment(UUID id);
}
