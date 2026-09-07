package org.acme.opentelemetry;


import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;



@Path("/bookings")
public class BookingResource {

    private static final Logger LOG =
            Logger.getLogger(
                    BookingResource.class
            );

    @Inject
    BookingService bookingService;

    @Inject
    FlightRepository flightRepository;

    @POST
    @Path("/{bookingId}")
    public BookingService.BookingConfirmation
    createBooking(
            @PathParam("bookingId")
            String bookingId,

            @QueryParam("flightId")
            String flightId) {

        try {

            return bookingService.createBooking(
                    bookingId,
                    flightId
            );

        } catch (IllegalStateException e) {

            LOG.errorf(e,
                            "BOOKING_FAILED " +
                            "bookingId=%s " +
                            "flightId=%s " +
                            "errorType=%s " +
                            "message=%s",

                            bookingId,
                            flightId,
                            e.getClass().getName(),
                            e.getMessage()

            );

            throw new WebApplicationException(
                    e.getMessage(),
                    e,
                    Response.Status.CONFLICT
            );

        } catch (Exception e) {

            LOG.errorf(e,
                            "BOOKING_FAILED " +
                            "bookingId=%s " +
                            "flightId=%s " +
                            "errorType=%s " +
                            "message=%s",

                            bookingId,
                            flightId,
                            e.getClass().getName(),
                            e.getMessage()

            );

            throw e;
        }
    }

    @POST
    @Path("/flight/{flightId}/gate")
    public void assignGate(
            @PathParam("flightId")
            String flightId,

            @QueryParam("gate")
            String gate) {

        flightRepository.assignGate(
                flightId,
                gate
        );

        LOG.infof(
                "GATE_ASSIGNED " +
                "flightId=%s gate=%s",
                flightId,
                gate
        );
    }
}
