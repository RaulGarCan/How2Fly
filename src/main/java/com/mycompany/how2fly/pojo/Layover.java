package com.mycompany.how2fly.pojo;

public class Layover {
    private int duration;
    private String name;
    private String id;
    private boolean overnight;

    public Layover() {
    }

    public Layover(int duration, String name, String id, boolean overnight) {
        this.duration = duration;
        this.name = name;
        this.id = id;
        this.overnight = overnight;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOvernight() {
        return overnight;
    }

    public void setOvernight(boolean overnight) {
        this.overnight = overnight;
    }

    @Override
    public String toString() {
        return "Layover{" +
                "duration=" + duration +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", overnight=" + overnight +
                '}';
    }
}
