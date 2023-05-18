package acn.intern.appointmentservice.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    private UUID id;

    @NotNull
    private String start;

    @NotNull
    private String end;

    @NotNull
    private UUID specialistId;

    @NotNull
    private int roomId;

    @NotNull
    private double price;
}
