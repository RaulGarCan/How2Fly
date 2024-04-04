package com.mycompany.how2fly.pojo.frontend;

import com.mycompany.how2fly.pojo.BestFlights;
import com.mycompany.how2fly.pojo.Flight;
import com.mycompany.how2fly.pojo.Layover;
import com.mycompany.how2fly.pojo.OtherFlights;

import java.util.ArrayList;

public class FlightDetails {

    private final ArrayList<Flight> flights;
    private final ArrayList<Layover> layovers;
    private final int total_duration;
    private final int price;
    private final String airline_logo;
    private final String departure_token;
    private final String booking_token;
    private final boolean isBestFlight;

    public FlightDetails(BestFlights bestFlights) {
        this.flights = bestFlights.getFlights();
        this.layovers = bestFlights.getLayovers();
        this.total_duration = bestFlights.getTotal_duration();
        this.price = bestFlights.getPrice();
        this.airline_logo = bestFlights.getAirline_logo();
        this.departure_token = bestFlights.getDeparture_token();
        this.booking_token = bestFlights.getBooking_token();
        isBestFlight = true;
    }

    public FlightDetails(OtherFlights otherFlights) {
        this.flights = otherFlights.getFlights();
        this.layovers = otherFlights.getLayovers();
        this.total_duration = otherFlights.getTotal_duration();
        this.price = otherFlights.getPrice();
        this.airline_logo = otherFlights.getAirline_logo();
        this.departure_token = otherFlights.getDeparture_token();
        this.booking_token = otherFlights.getBooking_token();
        isBestFlight = false;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public ArrayList<Layover> getLayovers() {
        return layovers;
    }

    public int getTotal_duration() {
        return total_duration;
    }

    public int getPrice() {
        return price;
    }

    public String getAirline_logo() {
        return airline_logo;
    }

    public String getDeparture_token() {
        return departure_token;
    }

    public String getBooking_token() {
        return booking_token;
    }

    public boolean isIsBestFlight() {
        return isBestFlight;
    }
    
    @Override
    public String toString() {
        return "FlightDetails{"
                + "flights=" + flights
                + ", layovers=" + layovers
                + ", total_duration=" + total_duration
                + ", price=" + price
                + ", airline_logo='" + airline_logo + '\''
                + ", departure_token='" + departure_token + '\''
                + ", booking_token='" + booking_token + '\''
                + '}';
    }
}
