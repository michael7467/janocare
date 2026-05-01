package domain.services;

import domain.entities.Professional;
import domain.entities.Specialization;
import domain.exceptions.SpecializationAlreadyExistsException;

public class ProfessionalDomainService {

    public void addSpecialization(Professional professional, Specialization specialization) {
        boolean exists = professional.getSpecializations().stream()
                .anyMatch(s -> s.getName().equals(specialization.getName()));

        if (exists)
            throw new SpecializationAlreadyExistsException("Specialization already exists");

        professional.addSpecialization(specialization);
    }
}
