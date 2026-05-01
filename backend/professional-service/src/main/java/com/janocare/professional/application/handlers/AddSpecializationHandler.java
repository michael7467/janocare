package application.handlers;

import application.commands.AddSpecializationCommand;
import application.ports.ProfessionalRepository;
import domain.entities.Professional;
import domain.entities.Specialization;
import domain.services.ProfessionalDomainService;
import domain.valueobjects.SpecialtyName;

import java.util.UUID;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddSpecializationHandler {

    private final ProfessionalRepository repository;
    private final ProfessionalDomainService domainService = new ProfessionalDomainService();

    public AddSpecializationHandler(ProfessionalRepository repository) {
        this.repository = repository;
    }

    public void handle(AddSpecializationCommand command) {
        Professional professional = repository.findById(command.professionalId)
                .orElseThrow(() -> new RuntimeException("Professional not found"));

        Specialization specialization = new Specialization(
                UUID.randomUUID(),
                new SpecialtyName(command.specializationName)
        );

        domainService.addSpecialization(professional, specialization);

        repository.save(professional);
    }
}
