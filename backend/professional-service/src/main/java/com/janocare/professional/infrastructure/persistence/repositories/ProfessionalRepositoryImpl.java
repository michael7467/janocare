package infrastructure.persistence.repositories;

import application.ports.ProfessionalRepository;
import domain.entities.Professional;
import infrastructure.persistence.entities.ProfessionalEntity;
import infrastructure.persistence.mappers.ProfessionalMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProfessionalRepositoryImpl implements ProfessionalRepository {

    @Inject
    EntityManager em;

    @Override
    public void save(Professional professional) {
        ProfessionalEntity entity = ProfessionalMapper.toEntity(professional);
        em.merge(entity);
    }

    @Override
    public Optional<Professional> findById(UUID id) {
        ProfessionalEntity entity = em.find(ProfessionalEntity.class, id);
        return entity == null ? Optional.empty() : Optional.of(ProfessionalMapper.toDomain(entity));
    }

    @Override
    public List<Professional> searchBySpecialty(String specialty) {
        List<ProfessionalEntity> entities = em.createQuery(
                        "SELECT p FROM ProfessionalEntity p JOIN p.specializations s WHERE LOWER(s.name) = LOWER(:spec)",
                        ProfessionalEntity.class
                ).setParameter("spec", specialty)
                .getResultList();

        return entities.stream()
                .map(ProfessionalMapper::toDomain)
                .collect(Collectors.toList());
    }
}
