package acn.intern.appointmentservice.business.service.impl;

import acn.intern.appointmentservice.business.mappers.SpecialityMapper;
import acn.intern.appointmentservice.business.repository.SpecialityRepository;
import acn.intern.appointmentservice.business.repository.impl.SpecialityDAO;
import acn.intern.appointmentservice.business.service.SpecialityService;
import acn.intern.appointmentservice.model.Speciality;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class SpecialityServiceImpl implements SpecialityService {
    private final SpecialityRepository repository;

    private final SpecialityMapper mapper;

    @Override
    public List<Speciality> getAllSpecialities() {
        List<SpecialityDAO> SpecialityDAOs = repository.findAll();
        log.info("Found Specialities: {}", SpecialityDAOs.size());
        return SpecialityDAOs.stream().map(mapper::specialityDAOToSpeciality).collect(Collectors.toList());
    }

    @Override
    public Optional<Speciality> getSpecialityById(Integer id) {
        Optional<SpecialityDAO> SpecialityDAO = repository.findById(id);
        if (SpecialityDAO.isEmpty()) {
            log.error("Speciality with id {} not found", id);
            return Optional.empty();
        } else return Optional.of(mapper.specialityDAOToSpeciality(SpecialityDAO.get()));
    }

    @Override
    public Speciality postSpeciality(Speciality Speciality) {
        SpecialityDAO newSpecialityDAO = repository.save(mapper.specialityToSpecialityDAO(Speciality));
        Speciality newSpeciality = mapper.specialityDAOToSpeciality(newSpecialityDAO);
        log.info("Speciality saved: {}", newSpeciality);
        return newSpeciality;
    }

    @Override
    public Speciality updateSpeciality(Integer id, Speciality Speciality) {
        Speciality.setId(id);
        SpecialityDAO updatedSpecialityDAO = repository.save(mapper.specialityToSpecialityDAO(Speciality));
        Speciality updatedSpeciality = mapper.specialityDAOToSpeciality(updatedSpecialityDAO);
        log.info("Updated Speciality: {}", updatedSpeciality);
        return updatedSpeciality;
    }

    @Override
    public void deleteSpeciality(Integer id) {
        log.info("Deleting Speciality: {}", id);
        repository.deleteById(id);
    }

}
