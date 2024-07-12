package com.example.springsecurity.lowleveldesignpattern.creational.prototype;

public class UserPrototype {
    public static void main(String[] args) {
        User normalUser = new NormalUser("ravi");
        User clone = normalUser.clone();
        System.out.println(normalUser.getName()+"   "+clone.getName());

    }
}
interface User{
    User clone();
    String getName();
}

class NormalUser implements User{
    private String name;
    public NormalUser(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public User clone() {
        return new NormalUser(this.name);
    }
}
