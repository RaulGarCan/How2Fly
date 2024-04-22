/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.how2fly.pojo;

/**
 *
 * @author Alumno
 */
public class ApiKey {
    private String key;

    public ApiKey() {
    }

    public ApiKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ApiKey{" + "key=" + key + '}';
    }
    
}
