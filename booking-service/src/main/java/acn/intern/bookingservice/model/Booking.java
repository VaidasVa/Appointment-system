package acn.intern.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private UUID id;

    private String appointmentId;

    private String userId;

    private LocalDateTime bookingDate;

    private LocalDateTime cancellationDate;

    private String description;

}
