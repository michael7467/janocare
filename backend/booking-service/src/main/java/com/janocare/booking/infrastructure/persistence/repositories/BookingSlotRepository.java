package com.janocare.booking.infrastructure.persistence.repositories;

import com.janocare.booking.application.ports.BookingSlotRepositoryPort;
import com.janocare.booking.domain.entities.BookingSlot;
import com.janocare.booking.infrastructure.persistence.entities.BookingSlotJpaEntity;
import com.janocare.booking.infrastructure.persistence.mappers.BookingSlotPersistenceMapper;
import com.janocare.booking.domain.enums.BookingSlotStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import com.janocare.booking.infrastructure.persistence.mappers.BookingSlotPersistenceMapper;

@ApplicationScoped
public class BookingSlotRepository
        implements PanacheRepositoryBase<BookingSlotJpaEntity, UUID>,
        BookingSlotRepositoryPort {

    @Override
    @Transactional
    public BookingSlot save(BookingSlot slot) {
        BookingSlotJpaEntity entity =
                BookingSlotPersistenceMapper.toJpaEntity(slot);

        BookingSlotJpaEntity saved =
                getEntityManager().merge(entity);

        return BookingSlotPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<BookingSlot> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(BookingSlotPersistenceMapper::toDomain);
    }

    @Override
    public List<BookingSlot> findByProfessionalId(UUID professionalId) {
        return find("professionalId", professionalId)
                .list()
                .stream()
                .map(BookingSlotPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<BookingSlot> findAllSlots() {
        return listAll()
                .stream()
                .map(BookingSlotPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteSlotById(UUID id) {
        deleteById(id);
    }

    @Override
    public Map<String, List<BookingSlot>> findGroupedByMonth(UUID professionalId) {
        return find("professionalId", professionalId)
                .list()
                .stream()
                .map(BookingSlotPersistenceMapper::toDomain)
                .collect(Collectors.groupingBy(slot ->
                        slot.getSlotDate().getYear() + "-" +
                        String.format("%02d", slot.getSlotDate().getMonthValue())
                ));
    }
        @Override
        public List<BookingSlot> findByProfessionalIdAndSlotDate(UUID professionalId, LocalDate slotDate) {
        return find("professionalId = ?1 and slotDate = ?2", professionalId, slotDate)
                .list()
                .stream()
                .map(BookingSlotPersistenceMapper::toDomain)
                .toList();
        }
   @Override
public List<Map<String, Object>> findAnalyzedSlots(UUID professionalId) {
    List<BookingSlot> slots = findByProfessionalId(professionalId);

    Map<LocalDate, List<BookingSlot>> groupedByDate = slots.stream()
            .collect(Collectors.groupingBy(BookingSlot::getSlotDate));

    return groupedByDate.entrySet().stream()
            .map(entry -> {
                LocalDate date = entry.getKey();
                List<BookingSlot> daySlots = entry.getValue();

                long total = daySlots.size();

                long pending = daySlots.stream()
                        .filter(s -> s.getStatus() == BookingSlotStatus.AVAILABLE)
                        .count();
                long accepted = daySlots.stream()
                        .filter(s -> s.getStatus() == BookingSlotStatus.BOOKED)
                        .count();
                long reserved = daySlots.stream()
                        .filter(s -> s.getStatus() == BookingSlotStatus.RESERVED)
                        .count();
                long cancelled = daySlots.stream()
                        .filter(s -> s.getStatus() == BookingSlotStatus.CANCELLED)
                        .count();

                Integer slotInterval = daySlots.get(0).getSlotInterval();

                return Map.<String, Object>of(
                        "slotDate", date.toString(),
                        "slotInterval", slotInterval != null ? slotInterval : 45,
                        "Total", total,
                        "PENDING", pending,
                        "ACCEPTED", accepted,
                        "COMPLETED", reserved,
                        "CANCELLED", cancelled,
                        "REJECTED", 0L
                );
            })
            .sorted((a, b) -> b.get("slotDate").toString()
                    .compareTo(a.get("slotDate").toString()))
            .toList();
}
}