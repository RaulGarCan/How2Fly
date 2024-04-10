/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.how2fly.pojo.frontend;

/**
 *
 * @author Alumno
 */
public class AirportList {
    private String iataCode;
    private String locationName;
    private String location;
    private String country;
    
    public AirportList(){
        
    }

    public AirportList(String iataCode, String locationName, String location, String country) {
        this.iataCode = iataCode;
        this.locationName = locationName;
        this.location = location;
        this.country = country;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "AirportList{" + "iataCode=" + iataCode + ", locationName=" + locationName + ", location=" + location + ", country=" + country + '}';
    }
    public String toJson(){
        String result = "{";
        result += "\"iataCode\":\""+getIataCode().replaceAll("\"","'")+"\",";
        result += "\"locationName\":\""+getLocationName().replaceAll("\"","'")+"\",";
        result += "\"location\":\""+getLocation().replaceAll("\"","'")+"\",";
        result += "\"country\":\""+getCountry().replaceAll("\"","'")+"\"";
        result += "}";
        return result;
    }
    
}
