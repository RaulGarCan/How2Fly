package com.mycompany.how2fly.pojo;

public class SearchMetadata {
    private String id;
    private String status;
    private String json_endpoint;
    private String created_at;
    private String processed_at;
    private String google_flights_url;
    private String raw_html_file;
    private String prettify_html_file;
    private String total_time_taken;

    public SearchMetadata() {
    }

    public SearchMetadata(String id, String status, String json_endpoint, String created_at, String processed_at, String google_flights_url, String raw_html_file, String prettify_html_file, String total_time_taken) {
        this.id = id;
        this.status = status;
        this.json_endpoint = json_endpoint;
        this.created_at = created_at;
        this.processed_at = processed_at;
        this.google_flights_url = google_flights_url;
        this.raw_html_file = raw_html_file;
        this.prettify_html_file = prettify_html_file;
        this.total_time_taken = total_time_taken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJson_endpoint() {
        return json_endpoint;
    }

    public void setJson_endpoint(String json_endpoint) {
        this.json_endpoint = json_endpoint;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getProcessed_at() {
        return processed_at;
    }

    public void setProcessed_at(String processed_at) {
        this.processed_at = processed_at;
    }

    public String getGoogle_flights_url() {
        return google_flights_url;
    }

    public void setGoogle_flights_url(String google_flights_url) {
        this.google_flights_url = google_flights_url;
    }

    public String getRaw_html_file() {
        return raw_html_file;
    }

    public void setRaw_html_file(String raw_html_file) {
        this.raw_html_file = raw_html_file;
    }

    public String getPrettify_html_file() {
        return prettify_html_file;
    }

    public void setPrettify_html_file(String prettify_html_file) {
        this.prettify_html_file = prettify_html_file;
    }

    public String getTotal_time_taken() {
        return total_time_taken;
    }

    public void setTotal_time_taken(String total_time_taken) {
        this.total_time_taken = total_time_taken;
    }

    @Override
    public String toString() {
        return "SearchMetadata{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", json_endpoint='" + json_endpoint + '\'' +
                ", created_at='" + created_at + '\'' +
                ", processed_at='" + processed_at + '\'' +
                ", google_flights_url='" + google_flights_url + '\'' +
                ", raw_html_file='" + raw_html_file + '\'' +
                ", prettify_html_file='" + prettify_html_file + '\'' +
                ", total_time_taken='" + total_time_taken + '\'' +
                '}';
    }
}
