package com.example.client.utils;

public class groupGame {
    public String team;
    public String team1;
    public String score;
    public String score1;
    public String year;
    public String state;//未开始 进行中 已结束
    public String state1;//小组赛 半决赛 决赛

    public groupGame(String team, String team1, String score, String score1, String year, String state, String state1){
        this.team=team;
        this.team1=team1;
        this.year=year;
        this.score=score;
        this.score1=score1;
        this.state=state;
        this.state1=state1;
    }
}
