package acn.intern.appointmentservice.web.controller;

import acn.intern.appointmentservice.business.service.RoomService;
import acn.intern.appointmentservice.model.Room;
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
@RequestMapping("api/v1/room")
public class RoomController {

    private final RoomService service;

    @GetMapping
    private ResponseEntity<List<Room>> getAllRooms() {
        log.info("Searching for all rooms...");
        List<Room> rooms = service.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Room> getRoomById(@PathVariable Integer id) {
        Optional<Room> Room = service.getRoomById(id);
        if (Room.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("Room found: {}", Room.get());
        return ResponseEntity.ok(Room.get());
    }

    @PostMapping
    public ResponseEntity<Room> postRoom(@Valid @RequestBody Room Room,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Invalid input");
            return ResponseEntity.badRequest().build();
        }
        Room savedRoom = service.postRoom(Room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> putRoom(@NonNull @PathVariable(name = "id") Integer id,
                                                @Valid @RequestBody Room Room,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Invalid input");
            return ResponseEntity.badRequest().build();
        }
        if (service.getRoomById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Room updatedRoom = service.updateRoom(id, Room);
        log.info("Updated Room: {}", updatedRoom);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@NonNull @PathVariable Integer id) {
        if (service.getRoomById(id).isEmpty()) {
            log.error("Room not found: {}", id);
            return ResponseEntity.notFound().build();
        }
        service.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
