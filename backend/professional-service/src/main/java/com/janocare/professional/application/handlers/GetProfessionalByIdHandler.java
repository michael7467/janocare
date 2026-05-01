package application.handlers;

import application.dto.ProfessionalDTO;
import application.mappers.ProfessionalMapper;
import application.ports.ProfessionalRepository;
import application.queries.GetProfessionalByIdQuery;
import domain.entities.Professional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class GetProfessionalByIdHandler {

    private final ProfessionalRepository repository;

    public GetProfessionalByIdHandler(ProfessionalRepository repository) {
        this.repository = repository;
    }

    public ProfessionalDTO handle(GetProfessionalByIdQuery query) {
        Professional professional = repository.findById(query.id)
                .orElseThrow(() -> new NotFoundException("Professional not found"));

        return ProfessionalMapper.toDTO(professional);
    }
}