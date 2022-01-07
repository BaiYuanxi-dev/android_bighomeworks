package com.example.client.user;

import java.io.Serializable;

public class Jifen implements Serializable {
    private String team;
    private String sum;
    private String win;
    private String lose;
    private String equal;
    private String jinqiu;
    private String score;

    public Jifen(String team, String sum, String win, String equal, String lose, String jinqiu, String score){
        this.team = team;
        this.sum = sum;
        this.win = win;
        this.lose = lose;
        this.equal = equal;
        this.jinqiu = jinqiu;
        this.score = score;
    }

    public String getTeam(){
        return this.team;
    }

    public String getSum(){
        return this.sum;
    }

    public String getWin(){
        return this.win;
    }

    public String getEqual(){
        return this.equal;
    }

    public String getLose(){
        return this.lose;
    }

    public String getJinqiu(){
        return this.jinqiu;
    }

    public String getScore(){
        return this.score;
    }
}
