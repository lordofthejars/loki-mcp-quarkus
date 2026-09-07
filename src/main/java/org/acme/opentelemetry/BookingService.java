package org.acme.opentelemetry;


import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BookingService {

    private static final Logger LOG =
            Logger.getLogger(
                    BookingService.class
            );

    @Inject
    FlightRepository flightRepository;

    public BookingConfirmation createBooking(
            String bookingId,
            String flightId) {

        LOG.infof(
                "Creating booking. bookingId=%s flightId=%s",
                bookingId,
                flightId
        );

        Flight flight =
                flightRepository.findById(flightId);

        if (flight == null) {
            throw new IllegalArgumentException(
                    "Flight not found: " + flightId
            );
        }

        String gate =
                flight.getDepartureGate();

        if (gate == null) {
            throw new IllegalStateException(
                    "Cannot book flight " + flightId +
                    ": departure gate has not been assigned yet"
            );
        }

        LOG.infof(
                "Processing flight. flightId=%s gate=%s",
                flightId,
                gate
        );

        int gateLength =
                gate.length();

        return new BookingConfirmation(
                bookingId,
                flight.getFlightNumber(),
                gate,
                gateLength
        );
    }

    public record BookingConfirmation(
            String bookingId,
            String flightNumber,
            String departureGate,
            int gateLength
    ) {
    }
}
