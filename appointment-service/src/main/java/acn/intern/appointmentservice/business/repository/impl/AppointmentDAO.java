package acn.intern.appointmentservice.business.repository.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    private LocalDateTime start;

    private LocalDateTime end;

    @ManyToOne(optional = false)
    @JoinColumn(name = "specialist_id", nullable = false)
    private SpecialistDAO specialist;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomDAO room;

    private double price;
}
