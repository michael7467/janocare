package com.janocare.booking.application.handlers;

import com.janocare.booking.application.commands.*;
import com.janocare.booking.application.ports.*;
import com.janocare.booking.application.queries.*;
import com.janocare.booking.domain.entities.*;
import com.janocare.booking.domain.enums.AppointmentBookingStatus;
import com.janocare.booking.domain.enums.BookingSlotStatus;
import com.janocare.booking.infrastructure.clients.payment.CreatePaymentRequest;
import com.janocare.booking.infrastructure.clients.payment.PaymentClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class BookingHandler {

    private static final Logger LOG =
            Logger.getLogger(BookingHandler.class);

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

    // =====================================================
    // BOOKING SLOTS
    // =====================================================

    @Transactional
    public BookingSlot createSlot(CreateBookingSlotCommand command) {
        BookingSlot slot = BookingSlot.create(
                command.professionalId,
                command.slotDate,
                command.startTime,
                command.endTime,
                command.slotInterval
        );
        BookingSlot saved = slotRepository.save(slot);
        LOG.infof("Booking slot created: %s for professional %s on %s",
                saved.getId(), command.professionalId, command.slotDate);
        return saved;
    }

    @Transactional
    public BookingSlot createSingleSlot(
            CreateSingleBookingSlotCommand command) {

        // slotInterval = 0 means single custom slot —
        // duration is endTime - startTime
        BookingSlot slot = BookingSlot.create(
                command.professionalId,
                command.slotDate,
                command.startTime,
                command.endTime,
                command.slotInterval != null
                        ? command.slotInterval : 0
        );
        return slotRepository.save(slot);
    }

    public BookingSlot findSlotById(FindBookingSlotByIdQuery query) {

        BookingSlot slot = slotRepository
                .findDomainById(query.slotId)
                .orElseThrow(() -> new NotFoundException(
                        "Booking slot not found: " + query.slotId));

        if (query.professionalId != null &&
                !slot.getProfessionalId()
                        .equals(query.professionalId)) {
            throw new NotFoundException(
                    "Booking slot not found for this professional");
        }

        return slot;
    }

    // findOrGenerateSlots — professional dashboard
    // returns existing slots OR auto-generates if none exist
    // NEVER called from public patient-facing endpoint
    public List<BookingSlot> findOrGenerateSlots(
            FindAllBookingSlotsQuery query) {

        if (query.professionalId != null
                && query.slotDate != null) {

            List<BookingSlot> existing =
                    slotRepository.findByProfessionalIdAndSlotDate(
                            query.professionalId,
                            query.slotDate
                    );

            if (!existing.isEmpty()) {
                return existing;
            }

            return generateWholeDaySlots(
                    query.professionalId, query.slotDate);
        }

        if (query.professionalId != null) {
            return slotRepository.findByProfessionalId(
                    query.professionalId);
        }

        return slotRepository.findAllSlots();
    }

    // findExistingSlots — public patient-facing endpoint
    // NEVER auto-generates — returns only existing AVAILABLE slots
    public List<BookingSlot> findExistingSlots(
            FindAllBookingSlotsQuery query) {

        if (query.professionalId != null
                && query.slotDate != null) {
            return slotRepository.findByProfessionalIdAndSlotDate(
                    query.professionalId, query.slotDate);
        }
        if (query.professionalId != null) {
            return slotRepository.findByProfessionalId(
                    query.professionalId);
        }
        return List.of();
    }

    @Transactional
    private List<BookingSlot> generateWholeDaySlots(
            UUID professionalId, LocalDate slotDate) {

        LOG.infof("Generating whole day slots for professional %s on %s",
                professionalId, slotDate);

        List<BookingSlot> generatedSlots = new ArrayList<>();

        LocalTime currentStart = LocalTime.of(8, 0);
        LocalTime dayEnd       = LocalTime.of(18, 0);
        int interval           = 45; // default — professional service
                                     // ProfessionType.slotInterval
                                     // should be passed here in production

        while (!currentStart.plusMinutes(interval).isAfter(dayEnd)) {
            LocalTime currentEnd = currentStart.plusMinutes(interval);

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

        LOG.infof("Generated %d slots for professional %s on %s",
                generatedSlots.size(), professionalId, slotDate);

        return generatedSlots;
    }

    @Transactional
    public BookingSlot updateSlot(UpdateBookingSlotCommand command) {

        BookingSlot slot = slotRepository
                .findDomainById(command.id)
                .orElseThrow(() -> new NotFoundException(
                        "Booking slot not found: " + command.id));

        if (!slot.getProfessionalId().equals(command.professionalId)) {
            throw new NotFoundException(
                    "Booking slot not found for this professional");
        }

        // parse status enum safely — use domain enum not raw String
        BookingSlotStatus statusEnum = command.status != null
                ? BookingSlotStatus.valueOf(
                        command.status.toUpperCase())
                : null;

        slot.update(
                command.slotDate,
                command.startTime,
                command.endTime,
                statusEnum    // enum — type-safe
        );

        BookingSlot saved = slotRepository.save(slot);
        LOG.infof("Booking slot updated: %s", command.id);
        return saved;
    }

    @Transactional
    public boolean deleteSlots(DeleteBookingSlotCommand command) {

        for (var id : command.ids) {
            BookingSlot slot = slotRepository
                    .findDomainById(id)
                    .orElseThrow(() -> new NotFoundException(
                            "Booking slot not found: " + id));

            if (!slot.getProfessionalId()
                    .equals(command.professionalId)) {
                throw new NotFoundException(
                        "Booking slot not found for this professional");
            }

            slotRepository.deleteSlotById(id);
            LOG.infof("Booking slot deleted: %s", id);
        }

        return true;
    }

    public Object findGroupedByMonth(
            FindGroupedBookingSlotsQuery query) {
        return slotRepository.findGroupedByMonth(
                query.professionalId);
    }

    public List<Map<String, Object>> findAnalyzedSlots(
            FindAnalyzedBookingSlotsQuery query) {
        return slotRepository.findAnalyzedSlots(
                query.professionalId);
    }

    // =====================================================
    // APPOINTMENT BOOKINGS
    // =====================================================

    @Transactional
    public AppointmentBooking createBooking(
            CreateAppointmentBookingCommand command) {

        // 1. find and book the slot — State: AVAILABLE → BOOKED
        BookingSlot slot = slotRepository
                .findDomainById(command.bookingSlotId)
                .orElseThrow(() -> new NotFoundException(
                        "Booking slot not found: "
                                + command.bookingSlotId));

        slot.book(); // domain method enforces state transition guard
        slotRepository.save(slot);

        // 2. create booking — starts at PENDING_PAYMENT
        AppointmentBooking booking = AppointmentBooking.create(
                command.patientUserId,
                command.professionalId,
                command.bookingSlotId,
                command.bookingDate,
                command.type,
                command.bookingReason,
                command.timezone
        );

        AppointmentBooking savedBooking =
                bookingRepository.save(booking);

        // 3. trigger payment — fault-tolerant
        // payment failure does not roll back the booking
        // prototype: hardcoded amount — production would fetch
        // consultationFee from professional service
        try {
            CreatePaymentRequest paymentReq =
                    new CreatePaymentRequest();
            paymentReq.appointmentBookingId = savedBooking.getId();
            paymentReq.patientUserId        = command.patientUserId;
            paymentReq.professionalId       = command.professionalId;
            paymentReq.amount               = BigDecimal.valueOf(100.00);
            paymentReq.currency             = "ETB";
            paymentReq.paymentType          = "BOOKING";
            paymentReq.transactionNote      =
                    "Appointment booking - " + command.type;

            paymentClient.createPayment(paymentReq);
            LOG.infof("Payment created for booking: %s",
                    savedBooking.getId());
        } catch (Exception e) {
            LOG.warnf("Payment service unavailable for booking %s: %s",
                    savedBooking.getId(), e.getMessage());
        }

        return savedBooking;
    }

    @Transactional
    public AppointmentBooking confirmBooking(
            ConfirmAppointmentBookingCommand command) {

        AppointmentBooking booking = bookingRepository
                .findDomainById(command.appointmentBookingId)
                .orElseThrow(() -> new NotFoundException(
                        "Appointment booking not found: "
                                + command.appointmentBookingId));

        booking.confirm(); // PENDING_PAYMENT → CONFIRMED
        AppointmentBooking saved = bookingRepository.save(booking);

        LOG.infof("Booking confirmed: %s",
                command.appointmentBookingId);
        return saved;
    }

    @Transactional
    public AppointmentBooking cancelBooking(
            CancelAppointmentBookingCommand command) {

        // 1. cancel the booking
        AppointmentBooking booking = bookingRepository
                .findDomainById(command.appointmentBookingId)
                .orElseThrow(() -> new NotFoundException(
                        "Appointment booking not found: "
                                + command.appointmentBookingId));

        booking.cancel(); // guard: cannot cancel COMPLETED
        booking = bookingRepository.save(booking);

        // 2. restore slot to AVAILABLE — patient can rebook
        BookingSlot slot = slotRepository
                .findDomainById(booking.getBookingSlotId())
                .orElseThrow(() -> new NotFoundException(
                        "Booking slot not found: "
                                + booking.getBookingSlotId()));

        slot.makeAvailable(); // BOOKED → AVAILABLE
        slotRepository.save(slot);

        // 3. record the cancellation reason
        BookingCancellationReason cancellation =
                BookingCancellationReason.create(
                        command.appointmentBookingId,
                        command.userId,
                        command.cancellationReasonId,
                        command.comment,
                        command.timezone
                );

        bookingCancellationReasonRepository.save(cancellation);

        LOG.infof("Booking cancelled: %s by user %s reason %s",
                command.appointmentBookingId,
                command.userId,
                command.cancellationReasonId);

        return booking;
    }

 public AppointmentBooking findBookingById(
        FindAppointmentBookingByIdQuery query) {

    AppointmentBooking booking = bookingRepository
            .findDomainById(query.appointmentBookingId)
            .orElseThrow(() -> new NotFoundException(
                    "Appointment booking not found: "
                            + query.appointmentBookingId));

    // professional ownership check
    if (query.professionalId != null &&
            !booking.getProfessionalId()
                    .equals(query.professionalId)) {
        throw new NotFoundException(
                "Booking not found for this professional");
    }

    // patient ownership check — ADD THIS
    if (query.patientUserId != null &&
            !booking.getPatientUserId()
                    .equals(query.patientUserId)) {
        throw new NotFoundException(
                "Booking not found for this patient");
    }

    return booking;
}

    public List<AppointmentBooking> findAllBookings(
            FindAllAppointmentBookingsQuery query) {

        if (query.patientUserId != null) {
            return bookingRepository.findByPatientUserId(
                    query.patientUserId);
        }
        if (query.professionalId != null) {
            return bookingRepository.findByProfessionalId(
                    query.professionalId);
        }
        return bookingRepository.findAllBookings();
    }

    @Transactional
    public AppointmentBooking updateBooking(
            UpdateAppointmentBookingCommand command) {

        AppointmentBooking booking = bookingRepository
                .findDomainById(command.appointmentBookingId)
                .orElseThrow(() -> new NotFoundException(
                        "Appointment booking not found: "
                                + command.appointmentBookingId));

        if (!booking.getProfessionalId()
                .equals(command.professionalId)) {
            throw new NotFoundException(
                    "Appointment booking not found " +
                    "for this professional");
        }

        // update uses enums directly — no raw String parsing
        booking.update(
                command.bookingDate,
                command.type,      // AppointmentBookingType enum
                command.bookingReason,
                command.timezone
                // status changes only through named methods:
                // confirm(), cancel(), complete(), reject()
        );

        AppointmentBooking saved = bookingRepository.save(booking);
        LOG.infof("Booking updated: %s", command.appointmentBookingId);
        return saved;
    }

    @Transactional
    public boolean deleteBooking(
            DeleteAppointmentBookingCommand command) {

        AppointmentBooking booking = bookingRepository
                .findDomainById(command.appointmentBookingId)
                .orElseThrow(() -> new NotFoundException(
                        "Appointment booking not found: "
                                + command.appointmentBookingId));

        if (!booking.getProfessionalId()
                .equals(command.professionalId)) {
            throw new NotFoundException(
                    "Appointment booking not found " +
                    "for this professional");
        }

        bookingRepository.deleteBookingById(
                command.appointmentBookingId);

        LOG.infof("Booking deleted: %s",
                command.appointmentBookingId);
        return true;
    }

    // =====================================================
    // CANCELLATION REASONS
    // =====================================================

    @Transactional
    public CancellationReason createCancellationReason(
            CreateCancellationReasonCommand command) {

        CancellationReason reason =
                CancellationReason.create(command.reason);

        CancellationReason saved = reasonRepository.save(reason);
        LOG.infof("Cancellation reason created: %s", saved.getId());
        return saved;
    }

    public List<CancellationReason> findAllCancellationReasons(
            FindAllCancellationReasonsQuery query) {
        return reasonRepository.findAllReasons();
    }

    public Object findBookingAnalytics(UUID professionalId) {
        return bookingRepository.findBookingAnalytics(professionalId);
    }
}