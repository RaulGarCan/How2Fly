package com.mycompany.how2fly;

import com.google.gson.Gson;
import com.mycompany.how2fly.pojo.BestFlights;
import com.mycompany.how2fly.pojo.OtherFlights;
import com.mycompany.how2fly.pojo.Response;
import com.mycompany.how2fly.pojo.frontend.FlightDetails;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String departureId = "CDG";
        String arrivalId = "AUS";
        String departureDate = "2024-03-27";
        //String returnDate = "2024-04-02";
        String currency = "USD";
        String type = "2"; // 1 Round (Def) | 2 OneWay
        //peticionAPI(departureId,arrivalId,departureDate,null,currency, type);

        //String rutaExample = "./src/main/java/org/example/data/example.json";
        String rutaData = "./src/main/java/org/example/cache/data.json";
        String json = leerJSON(rutaData);
        Response response = parsearJSON(json);

        ArrayList<BestFlights> bestFlights = response.getBest_flights();
        ArrayList<OtherFlights> otherFlights = response.getOther_flights();

        ArrayList<FlightDetails> flightDetails = getFrontEndDetails(bestFlights, otherFlights);
        for(FlightDetails details : flightDetails){
            System.out.println(details);
        }

    }
    public static void peticionAPI(String departureId, String arrivalId, String departureDate, String returnDate, String currency, String type){
        try {

            String engine = "google_flights";
            String api_key = "cb86689dbb1f68e6363ba7c5ecf4604177d0e21cd801037bd8e35ef77a43e271";

            String link = "https://serpapi.com/search.json?engine="+engine+"&departure_id="+departureId+"&arrival_id="+arrivalId+"&gl=us&hl=en";
            if(currency!=null && !currency.isBlank()) {
                link += "&currency=" + currency;
            }
            if(type!=null && !type.isBlank()){
                link += "&type="+type;
            }
            if(departureDate!=null && !departureDate.isBlank()) {
                link += "&outbound_date=" + departureDate;
            }
            if(returnDate!=null && !returnDate.isBlank()) {
                link += "&return_date=" + returnDate;
            }
            link+="&api_key="+api_key;

            System.out.println(link);
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

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
            guardarJSON(result);
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }
    public static void guardarJSON(String data){
        String ruta = "./src/main/java/org/example/cache/data.json";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(ruta));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Response parsearJSON(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Response.class);
    }
    public static String leerJSON(String ruta){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ruta));
            String line;
            String result = "";
            while((line=reader.readLine())!=null){
                result+=line;
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<FlightDetails> getFrontEndDetails(ArrayList<BestFlights> bestFlights, ArrayList<OtherFlights> otherFlights){
        ArrayList<FlightDetails> flightDetails = new ArrayList<>();
        for(BestFlights flights : bestFlights){
            flightDetails.add(new FlightDetails(flights));
        }
        for(OtherFlights flights : otherFlights){
            flightDetails.add(new FlightDetails(flights));
        }
        return flightDetails;
    }
}