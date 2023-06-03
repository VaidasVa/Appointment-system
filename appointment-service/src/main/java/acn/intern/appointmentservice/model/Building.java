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
public class Building {
    @Id
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String city;

    @NotNull
    private String postCode;

    @NotNull
    private String address;
}
