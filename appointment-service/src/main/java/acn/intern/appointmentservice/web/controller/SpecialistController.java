package acn.intern.appointmentservice.web.controller;

import acn.intern.appointmentservice.business.service.SpecialistService;
import acn.intern.appointmentservice.model.Specialist;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
@RequestMapping("api/v1/specialist")
public class SpecialistController {
    private final SpecialistService service;

    @GetMapping
    private ResponseEntity<List<Specialist>> getAllSpecialists() {
        log.info("Searching for all Specialists...");
        List<Specialist> Specialists = service.getAllSpecialists();
        return ResponseEntity.ok(Specialists);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Specialist> getSpecialistById(@PathVariable UUID id) {
        Optional<Specialist> Specialist = service.getSpecialistById(id);
        if (Specialist.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("Specialist found: {}", Specialist.get());
        return ResponseEntity.ok(Specialist.get());
    }

    @PostMapping
    public ResponseEntity<Specialist> postSpecialist(@Valid @RequestBody Specialist Specialist,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Invalid input");
            return ResponseEntity.badRequest().build();
        }
        Specialist savedSpecialist = service.postSpecialist(Specialist);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSpecialist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialist> putSpecialist(@NonNull @PathVariable(name = "id") UUID id,
                                                @Valid @RequestBody Specialist Specialist,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Invalid input");
            return ResponseEntity.badRequest().build();
        }
        if (service.getSpecialistById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Specialist updatedSpecialist = service.updateSpecialist(id, Specialist);
        log.info("Updated Specialist: {}", updatedSpecialist);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedSpecialist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialist(@NonNull @PathVariable UUID id) {
        if (service.getSpecialistById(id).isEmpty()) {
            log.error("Specialist not found: {}", id);
            return ResponseEntity.notFound().build();
        }
        service.deleteSpecialist(id);
        return ResponseEntity.noContent().build();
    }
}
