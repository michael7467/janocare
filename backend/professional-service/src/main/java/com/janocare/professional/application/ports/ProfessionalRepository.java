package application.ports;

import domain.entities.Professional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalRepository {
    void save(Professional professional);
    Optional<Professional> findById(UUID id);
    List<Professional> searchBySpecialty(String specialty);
}
