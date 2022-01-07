package com.example.client.user;

import java.io.Serializable;

public class Sheshou implements Serializable {
    private String name;
    private String team;
    private String jinqiu;
    private String dianqiu;

    public Sheshou(String name, String team, String jinqiu, String dianqiu){
        this.name = name;
        this.team = team;
        this.jinqiu = jinqiu;
        this.dianqiu = dianqiu;
    }

    public String getName(){
        return this.name;
    }

    public String getTeam(){
        return this.team;
    }

    public String getJinqiu(){
        return this.jinqiu;
    }

    public String getDianqiu(){
        return this.dianqiu;
    }
}
