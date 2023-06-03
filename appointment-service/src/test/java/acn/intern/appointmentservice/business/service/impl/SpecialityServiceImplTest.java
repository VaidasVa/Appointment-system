package acn.intern.appointmentservice.business.service.impl;

import acn.intern.appointmentservice.business.mappers.SpecialityMapper;
import acn.intern.appointmentservice.business.repository.SpecialityRepository;
import acn.intern.appointmentservice.business.repository.impl.SpecialityDAO;
import acn.intern.appointmentservice.model.Speciality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SpecialityServiceImplTest {

    private Speciality speciality;

    private SpecialityDAO specialityDAO;

    @Mock
    SpecialityRepository repository;

    @Mock
    SpecialityMapper mapper;

    @InjectMocks
    SpecialityServiceImpl service;

    @BeforeEach
    void initializeValues() {
        speciality = createSpeciality();
        specialityDAO = createSpecialityDAO();
    }

    @Test
    void getAllSpecialities() {
        when(repository.findAll()).thenReturn(List.of(specialityDAO)).thenReturn(new ArrayList<>());
        when(mapper.specialityDAOToSpeciality(specialityDAO)).thenReturn(speciality);
        assertEquals(List.of(speciality), service.getAllSpecialities());
        assertEquals(0, service.getAllSpecialities().size());
        verify(repository, times(2)).findAll();
    }

    @Test
    void getSpecialityById() {
        when(repository.findById(1))
                .thenReturn(Optional.ofNullable(specialityDAO))
                .thenReturn(Optional.empty());
        when(mapper.specialityDAOToSpeciality(specialityDAO)).thenReturn(speciality);
        assertEquals(Optional.of(speciality), service.getSpecialityById(1));
        assertEquals(Optional.empty(), service.getSpecialityById(1));
        verify(repository, times(2)).findById(any());
    }

    @Test
    void postSpeciality() {
        Speciality specialityWithoutId = createSpeciality();
        specialityWithoutId.setId(null);
        SpecialityDAO specialityDAOWithoutId = createSpecialityDAO();
        specialityDAOWithoutId.setId(null);

        when(repository.save(specialityDAOWithoutId)).thenReturn(specialityDAO);
        when(mapper.specialityToSpecialityDAO(specialityWithoutId)).thenReturn(specialityDAOWithoutId);
        when(mapper.specialityDAOToSpeciality(specialityDAO)).thenReturn(speciality);

        assertEquals(speciality, service.postSpeciality(specialityWithoutId));
        verify(repository, times(1)).save(specialityDAOWithoutId);
    }

    @Test
    void updateSpeciality() {
        when(repository.save(specialityDAO)).thenReturn(specialityDAO);
        when(mapper.specialityToSpecialityDAO(speciality)).thenReturn(specialityDAO);
        when(mapper.specialityDAOToSpeciality(specialityDAO)).thenReturn(speciality);

        assertEquals(speciality, service.updateSpeciality(1, speciality));
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteSpeciality() {
        assertDoesNotThrow(() -> service.deleteSpeciality(1));
        verify(repository, times(1)).deleteById(any());
    }

    private Speciality createSpeciality() {
        return Speciality.builder()
                .id(1)
                .name("Family doctor")
                .build();
    }

    private SpecialityDAO createSpecialityDAO() {
        return SpecialityDAO.builder()
                .id(1)
                .name("Family doctor")
                .build();
    }
}