package com.mycompany.how2fly.pojo;

public class CarbonEmission {
    private int this_flight;
    private int typical_for_this_route;
    private int difference_percent;

    public CarbonEmission() {
    }

    public CarbonEmission(int this_flight, int typical_for_this_route, int difference_percent) {
        this.this_flight = this_flight;
        this.typical_for_this_route = typical_for_this_route;
        this.difference_percent = difference_percent;
    }

    public int getThis_flight() {
        return this_flight;
    }

    public void setThis_flight(int this_flight) {
        this.this_flight = this_flight;
    }

    public int getTypical_for_this_route() {
        return typical_for_this_route;
    }

    public void setTypical_for_this_route(int typical_for_this_route) {
        this.typical_for_this_route = typical_for_this_route;
    }

    public int getDifference_percent() {
        return difference_percent;
    }

    public void setDifference_percent(int difference_percent) {
        this.difference_percent = difference_percent;
    }

    @Override
    public String toString() {
        return "CarbonEmission{" +
                "this_flight=" + this_flight +
                ", typical_for_this_route=" + typical_for_this_route +
                ", difference_percent=" + difference_percent +
                '}';
    }
}
