package com.example.client.user;

import java.io.Serializable;

public class Honghuang implements Serializable {
    private String name;
    private String team;
    private String yellow;
    private String red;

    public Honghuang(String name, String team, String yellow, String red){
        this.name = name;
        this.team = team;
        this.yellow = yellow;
        this.red = red;
    }

    public String getName(){
        return this.name;
    }

    public String getTeam(){
        return this.team;
    }

    public String getYellow(){
        return this.yellow;
    }

    public String getRed(){
        return this.red;
    }
}
