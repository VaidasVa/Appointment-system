package acn.intern.appointmentservice.business.repository.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "appointments")
public class AppointmentDAO {
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @NonNull
    private LocalDateTime start;

    @NonNull
    private LocalDateTime end;

    @NonNull
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "specialist_id", length = 36, columnDefinition = "varchar(36)")
    private UUID specialistId;

    @Column(name = "room_id")
    private int roomId;

    @NonNull
    private double price;
}
