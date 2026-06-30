package com.janocare.booking.application.ports;

import com.janocare.booking.domain.entities.CancellationReason;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CancellationReasonRepositoryPort {

    CancellationReason save(CancellationReason reason);

    Optional<CancellationReason> findDomainById(UUID id);

    List<CancellationReason> findAllReasons();

    void deleteReasonById(UUID id);
}