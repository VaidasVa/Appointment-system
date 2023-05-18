package acn.intern.appointmentservice.business.service.impl;

import acn.intern.appointmentservice.business.mappers.AppointmentMapper;
import acn.intern.appointmentservice.business.repository.AppointmentRepository;
import acn.intern.appointmentservice.business.repository.impl.AppointmentDAO;
import acn.intern.appointmentservice.business.service.AppointmentService;
import acn.intern.appointmentservice.model.Appointment;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;

    @Override
    public List<Appointment> getAllAppointments() {
        List<AppointmentDAO> appointmentDAOs = repository.findAll();
        log.info("Appointments found: {}", appointmentDAOs.size());
        return appointmentDAOs.stream().map(mapper::appointmentDAOToAppointment).collect(Collectors.toList());
    }

    @Override
    public Optional<Appointment> getAppointmentById(UUID id) {
        Optional<AppointmentDAO> appointmentOptional = repository.findById(id);

        if (appointmentOptional.isEmpty()) {
            log.info("Appointment with id {} was not found", id);
            return Optional.empty();
        } else {
            return Optional.of(mapper.appointmentDAOToAppointment(appointmentOptional.get()));
        }
    }

    @Override
    public Appointment postAppointment(Appointment appointment) {
        Appointment savedAppointment = mapper.appointmentDAOToAppointment(
                repository.save(mapper.appointmentToAppointmentDAO(appointment))
        );
        log.info("Saved successfully");
        return savedAppointment;
    }

    @Override
    public Appointment updateAppointment(UUID id, Appointment appointment) {
        appointment.setId(id);
        Appointment savedAppointment = mapper.appointmentDAOToAppointment(
                repository.save(mapper.appointmentToAppointmentDAO(appointment))
        );
        log.info("Updated appointment: {}", id);
        return savedAppointment;
    }

    @Override
    public void deleteAppointment(UUID id) {
        repository.deleteById(id);
        log.info("Deleted appointment by id: {}", id);
    }
}
