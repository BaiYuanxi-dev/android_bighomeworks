package com.example.homework3

public class Item constructor(imgId: Int, name:String,id :String ){
    public var imgId: Int;
    public var name:String = "";
    public var id :String = "";
    init{
        this.id = id;
        this.imgId = imgId;
        this.name = name;
    }
}


