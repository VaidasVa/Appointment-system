package acn.intern.appointmentservice.web.controller;

import acn.intern.appointmentservice.business.service.AppointmentService;
import acn.intern.appointmentservice.model.Appointment;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    private final Validator validator;
    private final AppointmentService service;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointmentList = service.getAllAppointments();
        log.info("Appointments: {}", appointmentList);
        return ResponseEntity.ok(appointmentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") UUID id) {
        Optional<Appointment> appointmentOptional = service.getAppointmentById(id);
        if (appointmentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("Appointment found: {}", appointmentOptional.get());
        return ResponseEntity.ok(appointmentOptional.get());
    }

    @PostMapping
    public ResponseEntity<Appointment> postAppointment(@Valid @RequestBody Appointment appointment,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Invalid input");
            return ResponseEntity.badRequest().build();
        }
        Appointment savedAppointment = service.postAppointment(appointment);
        log.info("Saved appointment: {}", savedAppointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> putAppointment(@NonNull @PathVariable(name = "id") UUID id,
                                                      @Valid @RequestBody Appointment appointment,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Invalid input");
            return ResponseEntity.badRequest().build();
        }
        if (service.getAppointmentById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Appointment updatedAppointment = service.updateAppointment(id, appointment);
        log.info("Updated appointment: {}", updatedAppointment);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@NonNull @PathVariable UUID id) {
        if (service.getAppointmentById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
