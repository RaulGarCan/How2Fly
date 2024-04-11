/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.how2fly.pojo.filters;

/**
 *
 * @author Alumno
 */
public enum FlightFilters {
    LAYOVER1 ("1 Layover"),
    LAYOVER2 ("2 Layovers"),
    LAYOVER3 ("3 Layovers"),
    LAYOVERS ("Rest of Layovers"),
    
    TIMEEARLY ("Early Time"),
    TIMEMID ("Mid Time"),
    TIMELATE ("Late Time"),
    
    PRICERANGE ("Price Range"),
    
    OVERNIGHT ("Overnight"),
    DELAYED ("Often Delayed");
    private String displayedName;

    private FlightFilters(String displayedName) {
        this.displayedName = displayedName;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }
    
}
