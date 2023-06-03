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
public class Specialist {
    @Id
    private UUID id;

    @NotNull
    private UUID userId;

    @NotNull
    private Speciality speciality;
}
