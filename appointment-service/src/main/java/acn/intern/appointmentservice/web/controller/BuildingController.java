package acn.intern.appointmentservice.web.controller;

import acn.intern.appointmentservice.business.service.BuildingService;
import acn.intern.appointmentservice.model.Building;
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
@RequestMapping("api/v1/building")
public class BuildingController {

    private final BuildingService service;

    @GetMapping
    private ResponseEntity<List<Building>> getAllBuildings() {
        log.info("Searching for all buildings...");
        List<Building> buildings = service.getAllBuildings();
        return ResponseEntity.ok(buildings);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Building> getBuildingById(@PathVariable Integer id) {
        Optional<Building> building = service.getBuildingById(id);
        if (building.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("Building found: {}", building.get());
        return ResponseEntity.ok(building.get());
    }

    @PostMapping
    public ResponseEntity<Building> postBuilding(@Valid @RequestBody Building building,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Invalid input");
            return ResponseEntity.badRequest().build();
        }
        Building savedBuilding = service.postBuilding(building);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBuilding);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Building> putBuilding(@NonNull @PathVariable(name = "id") Integer id,
                                                      @Valid @RequestBody Building building,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Invalid input");
            return ResponseEntity.badRequest().build();
        }
        if (service.getBuildingById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Building updatedBuilding = service.updateBuilding(id, building);
        log.info("Updated building: {}", updatedBuilding);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedBuilding);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(@NonNull @PathVariable Integer id) {
        if (service.getBuildingById(id).isEmpty()) {
            log.error("Building not found: {}", id);
            return ResponseEntity.notFound().build();
        }
        service.deleteBuilding(id);
        return ResponseEntity.noContent().build();
    }
}
