package application.mappers;

import application.dto.ProfessionalDTO;
import domain.entities.Professional;

import java.util.stream.Collectors;

public class ProfessionalMapper {

    public static ProfessionalDTO toDTO(Professional professional) {
        ProfessionalDTO dto = new ProfessionalDTO();
        dto.id = professional.getId();
        dto.fullName = professional.getName().full();
        dto.approvalStatus = professional.getApprovalStatus().name();

        dto.specializations = professional.getSpecializations()
                .stream()
                .map(s -> s.getName().getName())
                .collect(Collectors.toList());

        dto.reviews = professional.getReviews()
                .stream()
                .map(r -> r.getRating().getValue() + " - " + r.getComment())
                .collect(Collectors.toList());

        return dto;
    }
}
