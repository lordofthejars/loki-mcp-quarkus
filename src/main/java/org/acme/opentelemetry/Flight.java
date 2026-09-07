package org.acme.opentelemetry;


public class Flight {

    private final String id;
    private final String flightNumber;

    private String departureGate;

    public Flight(String id, String flightNumber) {
        this.id = id;
        this.flightNumber = flightNumber;
    }

    public String getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureGate() {
        return departureGate;
    }

    public void setDepartureGate(String departureGate) {
        this.departureGate = departureGate;
    }
}
