package com.mycompany.how2fly;

import com.google.gson.Gson;
import com.mycompany.how2fly.pojo.BestFlights;
import com.mycompany.how2fly.pojo.OtherFlights;
import com.mycompany.how2fly.pojo.Response;
import com.mycompany.how2fly.pojo.frontend.AirportList;
import com.mycompany.how2fly.pojo.frontend.FlightDetails;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        LocalDateTime a = LocalDateTime.now();
        System.out.println(a);
        //peticionAPI();
        /*
        Gson gson = new Gson();
        AirportList[] airports = gson.fromJson(leerJSON(MainFrame.pathCacheAirport), AirportList[].class);
        ArrayList<AirportList> tmp = new ArrayList<>();
        for (AirportList a : airports) {
            tmp.add(a);
        }
        tmp.sort(new Comparator<AirportList>() {
            @Override
            public int compare(AirportList o1, AirportList o2) {
                return o1.getIataCode().compareTo(o2.getIataCode());
            }
        });
        String json = gson.toJson(tmp.toArray(AirportList[]::new));
        guardarJSON(json, MainFrame.pathCacheAirport2);
        */
    }

    private ArrayList<AirportList> airportsPerLetter(char letter, AirportList[] airports) {
        ArrayList<AirportList> list = new ArrayList<>();
        for (AirportList a : airports) {
            if (a.getIataCode().charAt(0) == letter) {
                list.add(a);
            }
        }
        return list;
    }

    public static void peticionAPI() {
        try {
            //String link = "https://airport-info.p.rapidapi.com/airport";
            String link = "https://api.easypnr.com/v4/airports";
            System.out.println(link);
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            conn.setRequestProperty("X-Api-Key", "ljBmWbdoAangJbqlALAAAgvjfkdXiP");
            //conn.addRequestProperty("X-RapidAPI-Key", "d35b7f590bmsh1e7831b971b2199p11b091jsn474356c9150f");
            //conn.addRequestProperty("X-RapidAPI-Host", "airport-info.p.rapidapi.com");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            String result = "";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            System.out.println(result);
            guardarJSON(result, MainFrame.PATHCACHEAIRPORT);
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }

    public static void guardarJSON(String data, String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Response parsearJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Response.class);
    }

    public static String leerJSON(String ruta) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ruta));
            String line;
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<FlightDetails> getFrontEndDetails(ArrayList<BestFlights> bestFlights, ArrayList<OtherFlights> otherFlights) {
        ArrayList<FlightDetails> flightDetails = new ArrayList<>();
        for (BestFlights flights : bestFlights) {
            flightDetails.add(new FlightDetails(flights));
        }
        for (OtherFlights flights : otherFlights) {
            flightDetails.add(new FlightDetails(flights));
        }
        return flightDetails;
    }
}
