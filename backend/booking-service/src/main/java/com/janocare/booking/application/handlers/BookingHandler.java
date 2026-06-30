package com.janocare.booking.application.handlers;
import com.janocare.booking.infrastructure.clients.payment.CreatePaymentRequest;
import com.janocare.booking.infrastructure.clients.payment.PaymentClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

// keep your existing imports
import com.janocare.booking.application.commands.*;
import com.janocare.booking.application.ports.*;
import com.janocare.booking.application.queries.*;
import com.janocare.booking.domain.entities.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.UUID;
import java.util.Map;
import java.math.BigDecimal;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@ApplicationScoped
public class BookingHandler {

    @Inject
    BookingSlotRepositoryPort slotRepository;

    @Inject
    AppointmentBookingRepositoryPort bookingRepository;

    @Inject
    CancellationReasonRepositoryPort reasonRepository;

    @Inject
    BookingCancellationReasonRepositoryPort bookingCancellationReasonRepository;
    @Inject
    @RestClient
    PaymentClient paymentClient;
    
    private static final Logger log = Logger.getLogger(BookingHandler.class);
    // =========================
    // Booking Slots
    // =========================

    @Transactional
    public BookingSlot createSlot(CreateBookingSlotCommand command) {
        BookingSlot slot = BookingSlot.create(
                command.professionalId,
                command.slotDate,
                command.startTime,
                command.endTime,
                command.slotInterval
        );

        return slotRepository.save(slot);
    }

    @Transactional
    public BookingSlot createSingleSlot(CreateSingleBookingSlotCommand command) {
        BookingSlot slot = BookingSlot.create(
                command.professionalId,
                command.slotDate,
                command.startTime,
                command.endTime,
                0
        );

        return slotRepository.save(slot);
    }

    public BookingSlot findSlotById(FindBookingSlotByIdQuery query) {
        BookingSlot slot = slotRepository.findDomainById(query.slotId)
                .orElseThrow(() -> new NotFoundException("Booking slot not found"));

        if (query.professionalId != null &&
                !slot.getProfessionalId().equals(query.professionalId)) {
            throw new NotFoundException("Booking slot not found for this professional");
        }

        return slot;
    }

   public List<BookingSlot> findAllSlots(FindAllBookingSlotsQuery query) {

    if (query.professionalId != null && query.slotDate != null) {

        List<BookingSlot> existing =
                slotRepository.findByProfessionalIdAndSlotDate(
                        query.professionalId,
                        query.slotDate
                );

        if (!existing.isEmpty()) {
            return existing;
        }

        return generateWholeDaySlots(query.professionalId, query.slotDate);
    }

    if (query.professionalId != null) {
        return slotRepository.findByProfessionalId(query.professionalId);
    }

    return slotRepository.findAllSlots();
}

private List<BookingSlot> generateWholeDaySlots(UUID professionalId, LocalDate slotDate) {

    System.out.println("=== generateWholeDaySlots called ===");
    System.out.println("professionalId: " + professionalId);
    System.out.println("slotDate: " + slotDate);

    List<BookingSlot> generatedSlots = new ArrayList<>();

    LocalTime currentStart = LocalTime.of(8, 0);
    LocalTime dayEnd = LocalTime.of(18, 0);
    int interval = 45;

    while (!currentStart.plusMinutes(interval).isAfter(dayEnd)) {

        LocalTime currentEnd = currentStart.plusMinutes(interval);

        System.out.println("Generating slot: " + currentStart + " - " + currentEnd);

        BookingSlot slot = BookingSlot.create(
                professionalId,
                slotDate,
                currentStart,
                currentEnd,
                interval
        );

        generatedSlots.add(slotRepository.save(slot));

        currentStart = currentEnd;
    }

    System.out.println("=== Generated " + generatedSlots.size() + " slots ===");

    return generatedSlots;
}

public List<BookingSlot> findExistingSlots(FindAllBookingSlotsQuery query) {
    if (query.professionalId != null && query.slotDate != null) {
        return slotRepository.findByProfessionalIdAndSlotDate(
            query.professionalId, query.slotDate);
    }
    if (query.professionalId != null) {
        return slotRepository.findByProfessionalId(query.professionalId);
    }
    return List.of();
}
    @Transactional
    public BookingSlot updateSlot(UpdateBookingSlotCommand command) {
        BookingSlot slot = slotRepository
                .findDomainById(command.id)
                .orElseThrow(() -> new NotFoundException("Booking slot not found"));

        if (!slot.getProfessionalId().equals(command.professionalId)) {
            throw new NotFoundException("Booking slot not found for this professional");
        }

        slot.update(
                command.slotDate,
                command.startTime,
                command.endTime,
                command.status
        );

        return slotRepository.save(slot);
    }

    @Transactional
    public boolean deleteSlots(DeleteBookingSlotCommand command) {
        for (var id : command.ids) {
            BookingSlot slot = slotRepository
                    .findDomainById(id)
                    .orElseThrow(() -> new NotFoundException("Booking slot not found"));

            if (!slot.getProfessionalId().equals(command.professionalId)) {
                throw new NotFoundException("Booking slot not found for this professional");
            }

            slotRepository.deleteSlotById(id);
        }

        return true;
    }

    public Object findGroupedByMonth(FindGroupedBookingSlotsQuery query) {
        return slotRepository.findGroupedByMonth(query.professionalId);
    }

   public List<Map<String, Object>> findAnalyzedSlots(FindAnalyzedBookingSlotsQuery query) {
    return slotRepository.findAnalyzedSlots(query.professionalId);
}

    // =========================
    // Appointment Bookings
    // =========================

    @Transactional
    public AppointmentBooking createBooking(CreateAppointmentBookingCommand command) {
        BookingSlot slot = slotRepository.findDomainById(command.bookingSlotId)
                .orElseThrow(() -> new NotFoundException("Booking slot not found"));

        slot.book();
        slotRepository.save(slot);

        AppointmentBooking booking = AppointmentBooking.create(
                command.patientUserId,
                command.professionalId,
                command.bookingSlotId,
                command.bookingDate,
                command.type,
                command.bookingReason,
                command.timezone
        );

        AppointmentBooking savedBooking = bookingRepository.save(booking);

        // Create pending payment
        try {
            CreatePaymentRequest paymentReq = new CreatePaymentRequest();
            paymentReq.appointmentBookingId = savedBooking.getId();
            paymentReq.patientUserId = command.patientUserId;
            paymentReq.professionalId = command.professionalId;
            paymentReq.amount = BigDecimal.valueOf(100.00);
            paymentReq.currency = "ETB";
            paymentReq.paymentType = "BOOKING";
            paymentReq.transactionNote = "Appointment booking - " + command.type;

            paymentClient.createPayment(paymentReq);
            log.info("Payment created for booking: " + savedBooking.getId());
        } catch (Exception e) {
            log.warn("Payment service unavailable for booking " 
                + savedBooking.getId() + ": " + e.getMessage());
        }

        return savedBooking;
    }
    @Transactional
    public AppointmentBooking confirmBooking(ConfirmAppointmentBookingCommand command) {
        AppointmentBooking booking = bookingRepository
                .findDomainById(command.appointmentBookingId)
                .orElseThrow(() -> new NotFoundException("Appointment booking not found"));

        booking.confirm();

        return bookingRepository.save(booking);
    }

    @Transactional
    public AppointmentBooking cancelBooking(CancelAppointmentBookingCommand command) {
        AppointmentBooking booking = bookingRepository
                .findDomainById(command.appointmentBookingId)
                .orElseThrow(() -> new NotFoundException("Appointment booking not found"));

        booking.cancel();

        booking = bookingRepository.save(booking);

        BookingSlot slot = slotRepository
                .findDomainById(booking.getBookingSlotId())
                .orElseThrow(() -> new NotFoundException("Booking slot not found"));

        slot.cancel();
        slotRepository.save(slot);

        BookingCancellationReason cancellation =
                BookingCancellationReason.create(
                        command.appointmentBookingId,
                        command.userId,
                        command.cancellationReasonId,
                        command.comment,
                        command.timezone
                );

        bookingCancellationReasonRepository.save(cancellation);

        return booking;
    }

    public AppointmentBooking findBookingById(FindAppointmentBookingByIdQuery query) {
        AppointmentBooking booking = bookingRepository
                .findDomainById(query.appointmentBookingId)
                .orElseThrow(() -> new NotFoundException("Appointment booking not found"));

        if (query.professionalId != null &&
                !booking.getProfessionalId().equals(query.professionalId)) {
            throw new NotFoundException("Appointment booking not found for this professional");
        }

        return booking;
    }

    public List<AppointmentBooking> findAllBookings(FindAllAppointmentBookingsQuery query) {
        if (query.patientUserId != null) {
            return bookingRepository.findByPatientUserId(query.patientUserId);
        }

        if (query.professionalId != null) {
            return bookingRepository.findByProfessionalId(query.professionalId);
        }

        return bookingRepository.findAllBookings();
    }

    @Transactional
    public AppointmentBooking updateBooking(UpdateAppointmentBookingCommand command) {
        AppointmentBooking booking = bookingRepository
                .findDomainById(command.appointmentBookingId)
                .orElseThrow(() -> new NotFoundException("Appointment booking not found"));

        if (!booking.getProfessionalId().equals(command.professionalId)) {
            throw new NotFoundException("Appointment booking not found for this professional");
        }

        booking.update(
                command.bookingDate,
                command.type,
                command.bookingReason,
                command.timezone,
                command.status
        );

        return bookingRepository.save(booking);
    }

    @Transactional
    public boolean deleteBooking(DeleteAppointmentBookingCommand command) {
        AppointmentBooking booking = bookingRepository
                .findDomainById(command.appointmentBookingId)
                .orElseThrow(() -> new NotFoundException("Appointment booking not found"));

        if (!booking.getProfessionalId().equals(command.professionalId)) {
            throw new NotFoundException("Appointment booking not found for this professional");
        }

        bookingRepository.deleteBookingById(command.appointmentBookingId);

        return true;
    }

    // =========================
    // Cancellation Reasons
    // =========================

    @Transactional
    public CancellationReason createCancellationReason(CreateCancellationReasonCommand command) {
        CancellationReason reason = CancellationReason.create(command.reason);

        return reasonRepository.save(reason);
    }

    public List<CancellationReason> findAllCancellationReasons(
            FindAllCancellationReasonsQuery query
    ) {
        return reasonRepository.findAllReasons();
    }
    public Object findBookingAnalytics(UUID professionalId) {
    return bookingRepository.findBookingAnalytics(professionalId);
}
}