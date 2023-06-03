package acn.intern.appointmentservice.web.controller;

import acn.intern.appointmentservice.business.service.SpecialityService;
import acn.intern.appointmentservice.model.Speciality;
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

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/speciality")
public class SpecialityController {
    private final SpecialityService service;

    @GetMapping
    private ResponseEntity<List<Speciality>> getAllSpecialities() {
        log.info("Searching for all Specialities...");
        List<Speciality> Specialities = service.getAllSpecialities();
        return ResponseEntity.ok(Specialities);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Speciality> getSpecialityById(@PathVariable Integer id) {
        Optional<Speciality> Speciality = service.getSpecialityById(id);
        if (Speciality.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("Speciality found: {}", Speciality.get());
        return ResponseEntity.ok(Speciality.get());
    }

    @PostMapping
    public ResponseEntity<Speciality> postSpeciality(@Valid @RequestBody Speciality Speciality,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Invalid input");
            return ResponseEntity.badRequest().build();
        }
        Speciality savedSpeciality = service.postSpeciality(Speciality);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSpeciality);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Speciality> putSpeciality(@NonNull @PathVariable(name = "id") Integer id,
                                                @Valid @RequestBody Speciality Speciality,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Invalid input");
            return ResponseEntity.badRequest().build();
        }
        if (service.getSpecialityById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Speciality updatedSpeciality = service.updateSpeciality(id, Speciality);
        log.info("Updated Speciality: {}", updatedSpeciality);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedSpeciality);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpeciality(@NonNull @PathVariable Integer id) {
        if (service.getSpecialityById(id).isEmpty()) {
            log.error("Speciality not found: {}", id);
            return ResponseEntity.notFound().build();
        }
        service.deleteSpeciality(id);
        return ResponseEntity.noContent().build();
    }
}
