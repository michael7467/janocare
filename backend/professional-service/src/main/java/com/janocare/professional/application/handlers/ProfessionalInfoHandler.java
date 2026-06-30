package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalInfoApiMapper;
import com.janocare.professional.api.responses.professionalinfo.ProfessionalInfoResponse;
import com.janocare.professional.application.commands.professionalinfo.CreateProfessionalInfoCommand;
import com.janocare.professional.application.commands.professionalinfo.DeleteProfessionalInfoCommand;
import com.janocare.professional.application.commands.professionalinfo.UpdateProfessionalInfoCommand;
import com.janocare.professional.application.ports.ProfessionalInfoRepositoryPort;
import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.application.queries.professionalinfo.FindAllProfessionalInfosQuery;
import com.janocare.professional.application.queries.professionalinfo.FindProfessionalInfoByIdQuery;
import com.janocare.professional.application.queries.professionalinfo.FindProfessionalInfosByProfessionalIdQuery;
import com.janocare.professional.domain.entities.ProfessionalInfo;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProfessionalInfoHandler {

    @Inject
    ProfessionalInfoRepositoryPort infoRepository;

    @Inject
    ProfessionalRepositoryPort professionalRepository;

    @Transactional
    public ProfessionalInfoResponse create(CreateProfessionalInfoCommand command) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        ProfessionalInfo info = ProfessionalInfo.create(
                command.professionalId,
                command.institutionName,
                command.officeNumber,
                command.daysOfWeek,
                command.startTime,
                command.endTime,
                command.available
        );

        ProfessionalInfo saved = infoRepository.save(info);

        return ProfessionalInfoApiMapper.toResponse(saved);
    }

    @Transactional
    public ProfessionalInfoResponse update(UpdateProfessionalInfoCommand command) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.infoId == null) {
            throw new ValidationException("Professional info ID is required");
        }

        ProfessionalInfo info = infoRepository.findDomainById(command.infoId)
                .orElseThrow(() -> new ValidationException("Professional info not found"));

        info.update(
                command.institutionName,
                command.officeNumber,
                command.daysOfWeek,
                command.startTime,
                command.endTime,
                command.available
        );

        ProfessionalInfo saved = infoRepository.save(info);

        return ProfessionalInfoApiMapper.toResponse(saved);
    }

    @Transactional
    public void delete(DeleteProfessionalInfoCommand command) {
        if (command == null || command.infoId == null) {
            throw new ValidationException("Professional info ID is required");
        }

        infoRepository.findDomainById(command.infoId)
                .orElseThrow(() -> new ValidationException("Professional info not found"));

        infoRepository.deleteInfoById(command.infoId);
    }

    public ProfessionalInfoResponse findById(FindProfessionalInfoByIdQuery query) {
        if (query == null || query.infoId == null) {
            throw new ValidationException("Professional info ID is required");
        }

        ProfessionalInfo info = infoRepository.findDomainById(query.infoId)
                .orElseThrow(() -> new ValidationException("Professional info not found"));

        return ProfessionalInfoApiMapper.toResponse(info);
    }

    public List<ProfessionalInfoResponse> findByProfessionalId(
            FindProfessionalInfosByProfessionalIdQuery query
    ) {
        if (query == null || query.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(query.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        return infoRepository.findByProfessionalId(query.professionalId)
                .stream()
                .map(ProfessionalInfoApiMapper::toResponse)
                .toList();
    }

    public List<ProfessionalInfoResponse> findAll(FindAllProfessionalInfosQuery query) {
        return infoRepository.findAllProfessionalInfo()
                .stream()
                .map(ProfessionalInfoApiMapper::toResponse)
                .toList();
    }
}