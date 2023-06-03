package acn.intern.appointmentservice.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private Building building;
}
