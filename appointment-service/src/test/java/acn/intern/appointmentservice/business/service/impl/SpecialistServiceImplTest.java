package acn.intern.appointmentservice.business.service.impl;

import acn.intern.appointmentservice.business.mappers.SpecialistMapper;
import acn.intern.appointmentservice.business.repository.SpecialistRepository;
import acn.intern.appointmentservice.business.repository.impl.SpecialistDAO;
import acn.intern.appointmentservice.business.repository.impl.SpecialityDAO;
import acn.intern.appointmentservice.model.Specialist;
import acn.intern.appointmentservice.model.Speciality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SpecialistServiceImplTest {

    private Specialist specialist;

    private SpecialistDAO specialistDAO;

    @Mock
    SpecialistRepository repository;

    @Mock
    SpecialistMapper mapper;

    @InjectMocks
    SpecialistServiceImpl service;

    @BeforeEach
    void initializeValues() {
        specialist = createSpecialist();
        specialistDAO = createSpecialistDAO();
    }

    @Test
    void getAllSpecialists() {
        when(repository.findAll()).thenReturn(List.of(specialistDAO)).thenReturn(new ArrayList<>());
        when(mapper.specialistDAOToSpecialist(specialistDAO)).thenReturn(specialist);
        assertEquals(List.of(specialist), service.getAllSpecialists());
        assertEquals(0, service.getAllSpecialists().size());
        verify(repository, times(2)).findAll();
    }

    @Test
    void getSpecialistById() {
        when(repository.findById(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")))
                .thenReturn(Optional.ofNullable(specialistDAO))
                .thenReturn(Optional.empty());
        when(mapper.specialistDAOToSpecialist(specialistDAO)).thenReturn(specialist);
        assertEquals(Optional.of(specialist), service.getSpecialistById(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")));
        assertEquals(Optional.empty(), service.getSpecialistById(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")));
        verify(repository, times(2)).findById(any());
    }

    @Test
    void postSpecialist() {
        Specialist specialistWithoutId = createSpecialist();
        specialistWithoutId.setId(null);
        SpecialistDAO specialistDAOWithoutId = createSpecialistDAO();
        specialistDAOWithoutId.setId(null);

        when(repository.save(specialistDAOWithoutId)).thenReturn(specialistDAO);
        when(mapper.specialistToSpecialistDAO(specialistWithoutId)).thenReturn(specialistDAOWithoutId);
        when(mapper.specialistDAOToSpecialist(specialistDAO)).thenReturn(specialist);

        assertEquals(specialist, service.postSpecialist(specialistWithoutId));
        verify(repository, times(1)).save(specialistDAOWithoutId);
    }

    @Test
    void updateSpecialist() {
        when(repository.save(specialistDAO)).thenReturn(specialistDAO);
        when(mapper.specialistToSpecialistDAO(specialist)).thenReturn(specialistDAO);
        when(mapper.specialistDAOToSpecialist(specialistDAO)).thenReturn(specialist);

        assertEquals(specialist,
                service.updateSpecialist(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec"), specialist));
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteSpecialist() {
        assertDoesNotThrow(() -> service.deleteSpecialist(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec")));
        verify(repository, times(1)).deleteById(any());
    }


    private Specialist createSpecialist() {
        return Specialist.builder()
                .id(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec"))
                .userId(UUID.fromString("1e5cf2f4-110f-4d59-9af5-0974afcd3b26"))
                .speciality(Speciality.builder()
                                .id(1)
                                .name("Family doctor")
                                .build())
                .build();
    }

    private SpecialistDAO createSpecialistDAO() {
        return SpecialistDAO.builder()
                .id(UUID.fromString("5d8bc9bc-bbb9-48f1-8616-258059bf71ec"))
                .userId(UUID.fromString("1e5cf2f4-110f-4d59-9af5-0974afcd3b26"))
                .speciality(SpecialityDAO.builder()
                        .id(1)
                        .name("Family doctor")
                        .build())
                .build();
    }
}