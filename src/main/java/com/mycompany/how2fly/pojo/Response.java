package com.mycompany.how2fly.pojo;

import java.util.ArrayList;

public class Response {
    private SearchMetadata search_metadata;
    private SearchParameters search_parameters;
    private ArrayList<BestFlights> best_flights;
    private ArrayList<OtherFlights> other_flights;

    public Response() {
    }

    public Response(SearchMetadata search_metadata, SearchParameters search_parameters, ArrayList<BestFlights> best_flights, ArrayList<OtherFlights> other_flights) {
        this.search_metadata = search_metadata;
        this.search_parameters = search_parameters;
        this.best_flights = best_flights;
        this.other_flights = other_flights;
    }

    public SearchMetadata getSearch_metadata() {
        return search_metadata;
    }

    public void setSearch_metadata(SearchMetadata search_metadata) {
        this.search_metadata = search_metadata;
    }

    public SearchParameters getSearch_parameters() {
        return search_parameters;
    }

    public void setSearch_parameters(SearchParameters search_parameters) {
        this.search_parameters = search_parameters;
    }

    public ArrayList<BestFlights> getBest_flights() {
        return best_flights;
    }

    public void setBest_flights(ArrayList<BestFlights> best_flights) {
        this.best_flights = best_flights;
    }

    public ArrayList<OtherFlights> getOther_flights() {
        return other_flights;
    }

    public void setOther_flights(ArrayList<OtherFlights> other_flights) {
        this.other_flights = other_flights;
    }

    @Override
    public String toString() {
        return "Response{" +
                "search_metadata=" + search_metadata +
                ", search_parameters=" + search_parameters +
                ", best_flights=" + best_flights +
                ", other_flights=" + other_flights +
                '}';
    }
}
