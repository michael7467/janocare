package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.Professional;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionTypeJpaEntity;

public class ProfessionalPersistenceMapper {

    private ProfessionalPersistenceMapper() {}

    public static ProfessionalJpaEntity toJpaEntity(
            Professional domain,
            ProfessionTypeJpaEntity professionType
    ) {
        ProfessionalJpaEntity entity = new ProfessionalJpaEntity();

        entity.id = domain.getId();
        entity.userId = domain.getUserId();
        entity.professionType = professionType;

        entity.practicingFrom = domain.getPracticingFrom();

        entity.consultationFee = domain.getConsultationFee();
        entity.bookingFee = domain.getBookingFee();
        entity.instantConsultationFee = domain.getInstantConsultationFee();

        entity.upVotes = domain.getUpVotes();
        entity.downVotes = domain.getDownVotes();
        entity.viewCounts = domain.getViewCounts();

        entity.bio = domain.getBio();
        entity.status = domain.getStatus();

        entity.verified = domain.isVerified();
        entity.inpersonEnabled = domain.isInpersonEnabled();
        entity.onlineConsultationEnabled = domain.isOnlineConsultationEnabled();
        entity.instantCallEnabled = domain.isInstantCallEnabled();

        entity.walletBalance = domain.getWalletBalance();
        entity.createdAt = domain.getCreatedAt();
        entity.updatedAt = domain.getUpdatedAt();

        return entity;
    }

    public static Professional toDomain(ProfessionalJpaEntity entity) {
        return Professional.restore(
                entity.id,
                entity.userId,
                entity.professionType.id,
                entity.practicingFrom,
                entity.consultationFee,
                entity.bookingFee,
                entity.instantConsultationFee,
                entity.upVotes,
                entity.downVotes,
                entity.viewCounts,
                entity.bio,
                entity.status,
                entity.verified,
                entity.inpersonEnabled,
                entity.onlineConsultationEnabled,
                entity.instantCallEnabled,
                entity.walletBalance,
                entity.createdAt,
                entity.updatedAt
        );
    }
}