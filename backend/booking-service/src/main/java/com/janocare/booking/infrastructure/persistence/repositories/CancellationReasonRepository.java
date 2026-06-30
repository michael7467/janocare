package com.janocare.booking.infrastructure.persistence.repositories;

import com.janocare.booking.application.ports.CancellationReasonRepositoryPort;
import com.janocare.booking.domain.entities.CancellationReason;
import com.janocare.booking.infrastructure.persistence.entities.CancellationReasonJpaEntity;
import com.janocare.booking.infrastructure.persistence.mappers.CancellationReasonPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CancellationReasonRepository
        implements PanacheRepositoryBase<CancellationReasonJpaEntity, UUID>,
        CancellationReasonRepositoryPort {

    @Override
    @Transactional
    public CancellationReason save(CancellationReason reason) {
        CancellationReasonJpaEntity entity =
                CancellationReasonPersistenceMapper.toJpaEntity(reason);

        CancellationReasonJpaEntity saved =
                getEntityManager().merge(entity);

        return CancellationReasonPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<CancellationReason> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(CancellationReasonPersistenceMapper::toDomain);
    }

    @Override
    public List<CancellationReason> findAllReasons() {
        return listAll()
                .stream()
                .map(CancellationReasonPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteReasonById(UUID id) {
        deleteById(id);
    }
}