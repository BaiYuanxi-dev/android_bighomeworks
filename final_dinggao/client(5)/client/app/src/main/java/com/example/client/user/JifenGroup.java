package com.example.client.user;

import java.io.Serializable;

public class JifenGroup implements Serializable {
    private boolean isOpen;
    private String mes;
    private int num;

    public JifenGroup(String mes, int num){
        this.mes = mes;
        this.num = num;
        this.isOpen = false;
    }

    public JifenGroup(boolean isOpen){
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getMes(){
        return this.mes;
    }

    public int getNum(){return this.num;}
}
