package org.acme.opentelemetry;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class FlightRepository {

    private final Map<String, Flight> flights =
            new ConcurrentHashMap<>();

    @PostConstruct
    void init() {

        Flight flight =
                new Flight("IB123", "IB123");

        flight.setDepartureGate("A1");

        flights.put(
                flight.getId(),
                flight
        );
    }

    public Flight findById(String id) {
        return flights.get(id);
    }

    public void assignGate(
            String id,
            String gate) {

        Flight flight =
                flights.get(id);

        if (flight != null) {
            flight.setDepartureGate(gate);
        }
    }
}
