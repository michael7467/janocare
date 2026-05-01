package application.ports;

import domain.entities.Specialization;

import java.util.UUID;
import java.util.Optional;

public interface SpecializationRepository {
    Optional<Specialization> findByName(String name);
}
