package com.example.springsecurity.lowleveldesignpattern.creational;

public class SingletonPattern {
    /*
    * Singleton Pattern means that only one object or instance should be present
    * Eg : DatabaseConnection object should have only one instance
    *
    */

    private static SingletonPattern instance;
    private SingletonPattern() {
        /*
        * making constructor private means that no one can create object of this class
        */
    }
    SingletonPattern getInstance(){
        if(instance == null){
            return new SingletonPattern();
        }
        return instance;
    }

}
