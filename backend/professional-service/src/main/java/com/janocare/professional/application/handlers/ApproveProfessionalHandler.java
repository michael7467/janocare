package application.handlers;

import application.commands.ApproveProfessionalCommand;
import application.ports.ProfessionalRepository;
import domain.entities.Professional;
import domain.exceptions.InvalidProfessionalStateException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApproveProfessionalHandler {

    private final ProfessionalRepository repository;

    public ApproveProfessionalHandler(ProfessionalRepository repository) {
        this.repository = repository;
    }

    public void handle(ApproveProfessionalCommand command) {
        Professional professional = repository.findById(command.professionalId)
                .orElseThrow(() -> new InvalidProfessionalStateException("Professional not found"));

        professional.approve();
        repository.save(professional);
    }
}
