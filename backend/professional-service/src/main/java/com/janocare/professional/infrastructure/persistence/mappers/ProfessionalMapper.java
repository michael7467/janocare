package infrastructure.persistence.mappers;

import domain.entities.*;
import domain.enums.ApprovalStatus;
import domain.valueobjects.FullName;
import infrastructure.persistence.entities.*;

import java.util.stream.Collectors;

public class ProfessionalMapper {

    public static ProfessionalEntity toEntity(Professional domain) {
        ProfessionalEntity entity = new ProfessionalEntity();
        entity.setId(domain.getId());
        entity.setFirstName(domain.getName().getFirstName());
        entity.setLastName(domain.getName().getLastName());
        entity.setApprovalStatus(domain.getApprovalStatus());
        return entity;
    }

    public static Professional toDomain(ProfessionalEntity entity) {
        Professional domain = new Professional(
                entity.getId(),
                new FullName(entity.getFirstName(), entity.getLastName())
        );

        if (entity.getApprovalStatus() == ApprovalStatus.APPROVED)
            domain.approve();

        return domain;
    }
}
