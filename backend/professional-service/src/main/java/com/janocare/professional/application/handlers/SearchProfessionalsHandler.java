package application.handlers;

import application.dto.ProfessionalDTO;
import application.mappers.ProfessionalMapper;
import application.ports.ProfessionalRepository;
import application.queries.SearchProfessionalsQuery;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SearchProfessionalsHandler {

    private final ProfessionalRepository repository;

    public SearchProfessionalsHandler(ProfessionalRepository repository) {
        this.repository = repository;
    }

    public List<ProfessionalDTO> handle(SearchProfessionalsQuery query) {
        return repository.searchBySpecialty(query.specialty)
                .stream()
                .map(ProfessionalMapper::toDTO)
                .collect(Collectors.toList());
    }
}
