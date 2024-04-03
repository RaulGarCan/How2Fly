package com.mycompany.how2fly.pojo;

import java.util.ArrayList;

public class OtherFlights {
    private ArrayList<Flight> flights;
    private ArrayList<Layover> layovers;
    private int total_duration;
    private CarbonEmission carbon_emissions;
    private int price;
    private String type;
    private String airline_logo;
    private ArrayList<String> extensions;
    private String departure_token;
    private String booking_token;

    public OtherFlights() {
    }

    public OtherFlights(ArrayList<Flight> flights, ArrayList<Layover> layovers, int total_duration, CarbonEmission carbon_emissions, int price, String type, String airline_logo, ArrayList<String> extensions, String departure_token, String booking_token) {
        this.flights = flights;
        this.layovers = layovers;
        this.total_duration = total_duration;
        this.carbon_emissions = carbon_emissions;
        this.price = price;
        this.type = type;
        this.airline_logo = airline_logo;
        this.extensions = extensions;
        this.departure_token = departure_token;
        this.booking_token = booking_token;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    public ArrayList<Layover> getLayovers() {
        return layovers;
    }

    public void setLayovers(ArrayList<Layover> layovers) {
        this.layovers = layovers;
    }

    public int getTotal_duration() {
        return total_duration;
    }

    public void setTotal_duration(int total_duration) {
        this.total_duration = total_duration;
    }

    public CarbonEmission getCarbon_emissions() {
        return carbon_emissions;
    }

    public void setCarbon_emissions(CarbonEmission carbon_emissions) {
        this.carbon_emissions = carbon_emissions;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAirline_logo() {
        return airline_logo;
    }

    public void setAirline_logo(String airline_logo) {
        this.airline_logo = airline_logo;
    }

    public ArrayList<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(ArrayList<String> extensions) {
        this.extensions = extensions;
    }

    public String getDeparture_token() {
        return departure_token;
    }

    public void setDeparture_token(String departure_token) {
        this.departure_token = departure_token;
    }

    public String getBooking_token() {
        return booking_token;
    }

    public void setBooking_token(String booking_token) {
        this.booking_token = booking_token;
    }

    @Override
    public String toString() {
        return "OtherFlights{" +
                "flights=" + flights +
                ", layovers=" + layovers +
                ", total_duration=" + total_duration +
                ", carbon_emissions=" + carbon_emissions +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", airline_logo='" + airline_logo + '\'' +
                ", extensions=" + extensions +
                ", departure_token='" + departure_token + '\'' +
                ", booking_token='" + booking_token + '\'' +
                '}';
    }
    public String shortToString() {
        String result = "\nOtherFlights{\n";
        for(Flight flight : flights){
            result += "\n"+flight.shortToString();
        }
        result+="\n";
        for(Layover layover : layovers){
            result += "\n"+layover.toString();
        }
        result += "\n\ntotal_duration=" + total_duration +
                ", price=" + price +
                ", airline_logo='" + airline_logo + '\'' +
                ", departure_token='" + departure_token + '\'' +
                ", booking_token='" + booking_token + '\'' +
                '}'+"\n";
        return result;
    }
}
