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

        LOG.infof(
                "Processing flight. flightId=%s gate=%s",
                flightId,
                gate
        );

        if (gate == null) {
            throw new IllegalStateException(
                    "Departure gate not yet assigned for flight: " + flightId
            );
        }

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
