package com.janocare.professional.infrastructure.persistence;

import com.janocare.professional.application.ports.ProfessionalMembershipRepositoryPort;
import com.janocare.professional.domain.entities.ProfessionalMembership;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalMembershipJpaEntity;
import com.janocare.professional.infrastructure.persistence.mappers.ProfessionalMembershipPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalMembershipRepository
        implements PanacheRepositoryBase<ProfessionalMembershipJpaEntity,UUID>,
        ProfessionalMembershipRepositoryPort {

    @Override
    @Transactional
    public ProfessionalMembership save(ProfessionalMembership membership) {
        ProfessionalJpaEntity professional =
                getEntityManager().find(
                        ProfessionalJpaEntity.class,
                        membership.getProfessionalId()
                );

        if (professional == null) {
            throw new RuntimeException("Professional not found");
        }

        ProfessionalMembershipJpaEntity entity =
                ProfessionalMembershipPersistenceMapper.toJpaEntity(
                        membership,
                        professional
                );

        ProfessionalMembershipJpaEntity saved =
                getEntityManager().merge(entity);

        return ProfessionalMembershipPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<ProfessionalMembership> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(ProfessionalMembershipPersistenceMapper::toDomain);
    }

    @Override
    public List<ProfessionalMembership> findByProfessionalId(UUID professionalId) {
        return find("professional.id", professionalId)
                .list()
                .stream()
                .map(ProfessionalMembershipPersistenceMapper::toDomain)
                .toList();
    }
    @Override
    public List<ProfessionalMembership> findAllMembership() {
        return listAll()
                .stream()
                .map(ProfessionalMembershipPersistenceMapper::toDomain)
                .toList();
    }
    @Override
    @Transactional
    public void deleteMembershipById(UUID id) {
        deleteById(id);
    }
}