/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.how2fly.pojo;

import java.util.Arrays;

/**
 *
 * @author Alumno
 */
public class PriceInsights {
    private int lowest_price;
    private String price_level;
    private int[] typical_price_range;
    private int[][] price_history;
    
    public PriceInsights(){
        
    }

    public PriceInsights(int lowest_price, String price_level, int[] typical_price_range, int[][] price_history) {
        this.lowest_price = lowest_price;
        this.price_level = price_level;
        this.typical_price_range = typical_price_range;
        this.price_history = price_history;
    }

    public int getLowest_price() {
        return lowest_price;
    }

    public void setLowest_price(int lowest_price) {
        this.lowest_price = lowest_price;
    }

    public String getPrice_level() {
        return price_level;
    }

    public void setPrice_level(String price_level) {
        this.price_level = price_level;
    }

    public int[] getTypical_price_range() {
        return typical_price_range;
    }

    public void setTypical_price_range(int[] typical_price_range) {
        this.typical_price_range = typical_price_range;
    }

    public int[][] getPrice_history() {
        return price_history;
    }

    public void setPrice_history(int[][] price_history) {
        this.price_history = price_history;
    }

    @Override
    public String toString() {
        String result = "PriceInsights{" + "lowest_price=" + lowest_price + ", price_level=" + price_level + ", typical_price_range=" + Arrays.toString(typical_price_range) + ", price_history="; 
        for(int[] i : price_history){
            result += Arrays.toString(i);
        }
        result += '}';
        return result;
    }
    
}
