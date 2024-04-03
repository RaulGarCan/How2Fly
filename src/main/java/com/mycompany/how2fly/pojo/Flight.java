package com.mycompany.how2fly.pojo;

import java.util.ArrayList;

public class Flight {
    private Airport departure_airport;
    private Airport arrival_airport;
    private int duration;
    private String airplane;
    private String airline;
    private String airline_logo;
    private  String travel_class;
    private String flight_number;
    private ArrayList<String> extensions;
    private ArrayList<String> tickets_also_sold_by;
    private String legroom;
    private boolean overnight;
    private boolean often_delayed_by_over_30_min;

    public Flight() {
    }

    public Flight(Airport departure_airport, Airport arrival_airport, int duration, String airplane, String airline, String airline_logo, String travel_class, String flight_number, ArrayList<String> extensions, ArrayList<String> tickets_also_sold_by, String legroom, boolean overnight, boolean often_delayed_by_over_30_min) {
        this.departure_airport = departure_airport;
        this.arrival_airport = arrival_airport;
        this.duration = duration;
        this.airplane = airplane;
        this.airline = airline;
        this.airline_logo = airline_logo;
        this.travel_class = travel_class;
        this.flight_number = flight_number;
        this.extensions = extensions;
        this.tickets_also_sold_by = tickets_also_sold_by;
        this.legroom = legroom;
        this.overnight = overnight;
        this.often_delayed_by_over_30_min = often_delayed_by_over_30_min;
    }

    public Airport getDeparture_airport() {
        return departure_airport;
    }

    public void setDeparture_airport(Airport departure_airport) {
        this.departure_airport = departure_airport;
    }

    public Airport getArrival_airport() {
        return arrival_airport;
    }

    public void setArrival_airport(Airport arrival_airport) {
        this.arrival_airport = arrival_airport;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAirplane() {
        return airplane;
    }

    public void setAirplane(String airplane) {
        this.airplane = airplane;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getAirline_logo() {
        return airline_logo;
    }

    public void setAirline_logo(String airline_logo) {
        this.airline_logo = airline_logo;
    }

    public String getTravel_class() {
        return travel_class;
    }

    public void setTravel_class(String travel_class) {
        this.travel_class = travel_class;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public ArrayList<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(ArrayList<String> extensions) {
        this.extensions = extensions;
    }

    public ArrayList<String> getTickets_also_sold_by() {
        return tickets_also_sold_by;
    }

    public void setTickets_also_sold_by(ArrayList<String> tickets_also_sold_by) {
        this.tickets_also_sold_by = tickets_also_sold_by;
    }

    public String getLegroom() {
        return legroom;
    }

    public void setLegroom(String legroom) {
        this.legroom = legroom;
    }

    public boolean isOvernight() {
        return overnight;
    }

    public void setOvernight(boolean overnight) {
        this.overnight = overnight;
    }

    public boolean isOften_delayed_by_over_30_min() {
        return often_delayed_by_over_30_min;
    }

    public void setOften_delayed_by_over_30_min(boolean often_delayed_by_over_30_min) {
        this.often_delayed_by_over_30_min = often_delayed_by_over_30_min;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "departure_airport=" + departure_airport +
                ", arrival_airport=" + arrival_airport +
                ", duration=" + duration +
                ", airplane='" + airplane + '\'' +
                ", airline='" + airline + '\'' +
                ", airline_logo='" + airline_logo + '\'' +
                ", travel_class='" + travel_class + '\'' +
                ", flight_number='" + flight_number + '\'' +
                ", extensions=" + extensions +
                ", tickets_also_sold_by=" + tickets_also_sold_by +
                ", legroom='" + legroom + '\'' +
                ", overnight=" + overnight +
                ", often_delayed_by_over_30_min=" + often_delayed_by_over_30_min +
                '}';
    }
    public String shortToString(){
        return "Flight{" +
                "departure_airport=" + departure_airport +
                ", arrival_airport=" + arrival_airport +
                ", duration=" + duration +
                ", airplane='" + airplane + '\'' +
                ", airline='" + airline + '\'' +
                ", airline_logo='" + airline_logo + '\'' +
                ", travel_class='" + travel_class + '\'' +
                ", flight_number='" + flight_number + '\'' +
                ", overnight=" + overnight +
                ", often_delayed_by_over_30_min=" + often_delayed_by_over_30_min +
                '}';
    }
}
