package com.example.client.utils;

public class game {
    public String name;
    public String sponsor;
    public String year;
    public String state;//未开始 进行中 已结束
    public String state1;//小组赛 半决赛 决赛

    public game(String name, String sponsor, String year, String state, String state1){
        this.name=name;
        this.year=year;
        this.sponsor=sponsor;
        this.state=state;
        this.state1=state1;
    }

}
