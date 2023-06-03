package acn.intern.appointmentservice.business.service.impl;

import acn.intern.appointmentservice.business.mappers.SpecialistMapper;
import acn.intern.appointmentservice.business.repository.SpecialistRepository;
import acn.intern.appointmentservice.business.repository.impl.SpecialistDAO;
import acn.intern.appointmentservice.business.service.SpecialistService;
import acn.intern.appointmentservice.model.Specialist;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository repository;
    private final SpecialistMapper mapper;

    @Override
    public List<Specialist> getAllSpecialists() {
        List<SpecialistDAO> SpecialistDAOs = repository.findAll();
        log.info("Found Specialists: {}", SpecialistDAOs.size());
        return SpecialistDAOs.stream().map(mapper::specialistDAOToSpecialist).collect(Collectors.toList());
    }

    @Override
    public Optional<Specialist> getSpecialistById(UUID id) {
        Optional<SpecialistDAO> SpecialistDAO = repository.findById(id);
        if (SpecialistDAO.isEmpty()) {
            log.error("Specialist with id {} not found", id);
            return Optional.empty();
        } else return Optional.of(mapper.specialistDAOToSpecialist(SpecialistDAO.get()));
    }

    @Override
    public Specialist postSpecialist(Specialist Specialist) {
        SpecialistDAO newSpecialistDAO = repository.save(mapper.specialistToSpecialistDAO(Specialist));
        Specialist newSpecialist = mapper.specialistDAOToSpecialist(newSpecialistDAO);
        log.info("Specialist saved: {}", newSpecialist);
        return newSpecialist;
    }

    @Override
    public Specialist updateSpecialist(UUID id, Specialist Specialist) {
        Specialist.setId(id);
        SpecialistDAO updatedSpecialistDAO = repository.save(mapper.specialistToSpecialistDAO(Specialist));
        Specialist updatedSpecialist = mapper.specialistDAOToSpecialist(updatedSpecialistDAO);
        log.info("Updated Specialist: {}", updatedSpecialist);
        return updatedSpecialist;
    }

    @Override
    public void deleteSpecialist(UUID id) {
        log.info("Deleting Specialist: {}", id);
        repository.deleteById(id);
    }
}
