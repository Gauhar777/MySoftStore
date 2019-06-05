package com.example.mysoftstore.Model;

public class Cart {

String name;
String pId;
String price;
String quanty;
String user;

public Cart(){

}

    public Cart(String name, String pId, String price, String quanty, String user) {
        this.name = name;
        this.pId = pId;
        this.price = price;
        this.quanty = quanty;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pId;
    }

    public void setPid(String pid) {
        this.pId = pid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuanty() {
        return quanty;
    }

    public void setQuanty(String quanty) {
        this.quanty = quanty;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
