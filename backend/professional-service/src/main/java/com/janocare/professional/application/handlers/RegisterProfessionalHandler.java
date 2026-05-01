package application.handlers;

import application.commands.RegisterProfessionalCommand;
import application.ports.ProfessionalRepository;
import domain.entities.Professional;
import domain.valueobjects.FullName;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RegisterProfessionalHandler {

    private final ProfessionalRepository repository;

    public RegisterProfessionalHandler(ProfessionalRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UUID handle(RegisterProfessionalCommand command) {
        Professional professional = new Professional(
                UUID.randomUUID(),
                new FullName(command.firstName, command.lastName)
        );

        repository.save(professional);
        return professional.getId();
    }
}