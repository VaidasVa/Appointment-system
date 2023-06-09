package acn.intern.appointmentservice.business.service.impl;

import acn.intern.appointmentservice.business.mappers.AppointmentMapper;
import acn.intern.appointmentservice.business.repository.AppointmentRepository;
import acn.intern.appointmentservice.business.repository.impl.AppointmentDAO;
import acn.intern.appointmentservice.business.repository.impl.BuildingDAO;
import acn.intern.appointmentservice.business.repository.impl.RoomDAO;
import acn.intern.appointmentservice.business.repository.impl.SpecialistDAO;
import acn.intern.appointmentservice.business.repository.impl.SpecialityDAO;
import acn.intern.appointmentservice.model.Appointment;

import acn.intern.appointmentservice.model.Building;
import acn.intern.appointmentservice.model.Room;
import acn.intern.appointmentservice.model.Specialist;
import acn.intern.appointmentservice.model.Speciality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppointmentServiceImplTest {

    private Appointment appointment;
    private AppointmentDAO appointmentDAO;

    @Mock
    AppointmentRepository repository;

    @Mock
    AppointmentMapper mapper;

    @InjectMocks
    AppointmentServiceImpl service;

    @BeforeEach
    void initializeValues() {
        appointment = createAppointment();
        appointmentDAO = createAppointmentDAO();
    }

    @Test
    void getAllAppointments() {
        when(repository.findAll()).thenReturn(List.of(appointmentDAO)).thenReturn(new ArrayList<>());
        when(mapper.appointmentDAOToAppointment(appointmentDAO)).thenReturn(appointment);
        assertEquals(List.of(appointment), service.getAllAppointments());
        assertEquals(0, service.getAllAppointments().size());
        verify(repository, times(2)).findAll();
    }

    @Test
    void getAppointmentById() {
        when(repository.findById(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696")))
                .thenReturn(Optional.ofNullable(appointmentDAO))
                .thenReturn(Optional.empty());
        when(mapper.appointmentDAOToAppointment(appointmentDAO)).thenReturn(appointment);
        assertEquals(Optional.of(appointment), service.getAppointmentById(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696")));
        assertEquals(Optional.empty(), service.getAppointmentById(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696")));
        verify(repository, times(2)).findById(any());
    }

    @Test
    void postAppointment() {
        Appointment appointmentWithoutId = createAppointment();
        appointmentWithoutId.setId(null);
        AppointmentDAO appointmentDAOWithoutId = createAppointmentDAO();
        appointmentDAOWithoutId.setId(null);

        when(repository.save(appointmentDAOWithoutId)).thenReturn(appointmentDAO);
        when(mapper.appointmentToAppointmentDAO(appointmentWithoutId)).thenReturn(appointmentDAOWithoutId);
        when(mapper.appointmentDAOToAppointment(appointmentDAO)).thenReturn(appointment);

        assertEquals(appointment, service.postAppointment(appointmentWithoutId));
        verify(repository, times(1)).save(appointmentDAOWithoutId);
    }

    @Test
    void updateAppointment() {
        when(repository.save(appointmentDAO)).thenReturn(appointmentDAO);
        when(mapper.appointmentToAppointmentDAO(appointment)).thenReturn(appointmentDAO);
        when(mapper.appointmentDAOToAppointment(appointmentDAO)).thenReturn(appointment);

        assertEquals(appointment,
                service.updateAppointment(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696"), appointment));
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteAppointment() {
        assertDoesNotThrow(() -> service.deleteAppointment(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696")));
        verify(repository, times(1)).deleteById(any());
    }

    private Appointment createAppointment() {
        return Appointment.builder().id(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696"))
                                    .start("2023-01-01 14:00:00")
                                    .end("2023-01-01 15:00:00")
                                    .specialist(Specialist.builder()
                                            .id(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec"))
                                            .userId(UUID.fromString("1e5cf2f4-110f-4d59-9af5-0974afcd3b26"))
                                            .speciality(Speciality.builder()
                                                    .id(1)
                                                    .name("Family doctor")
                                                    .build())
                                            .build())
                                    .room(Room.builder()
                                            .id(1)
                                            .name("Exam Room 1")
                                            .building(Building.builder()
                                                    .id(1)
                                                    .name("Building One")
                                                    .address("Broad st. 3")
                                                    .city("Vilnius")
                                                    .postCode("000123")
                                                    .build())
                                            .build())
                                    .price(15.00)
                                    .build();
    }

    private AppointmentDAO createAppointmentDAO() {
        return AppointmentDAO.builder().id(UUID.fromString("c700f297-5db1-4a03-a4f0-e9e174510696"))
                                        .start(LocalDateTime.of(2023, 1, 1, 14, 0, 0))
                                        .end(LocalDateTime.of(2023, 1, 1, 14, 0, 0))
                .specialist(SpecialistDAO.builder()
                        .id(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec"))
                        .userId(UUID.fromString("1e5cf2f4-110f-4d59-9af5-0974afcd3b26"))
                        .speciality(SpecialityDAO.builder()
                                .id(1)
                                .name("Family doctor")
                                .build())
                        .build())
                .room(RoomDAO.builder()
                        .id(1)
                        .name("Exam Room 1")
                        .building(BuildingDAO.builder()
                                .id(1)
                                .name("Room One")
                                .address("Broad st. 3")
                                .city("Vilnius")
                                .postCode("000123")
                                .build())
                                .build())
                .price(15.00)
                .build();
    }
}