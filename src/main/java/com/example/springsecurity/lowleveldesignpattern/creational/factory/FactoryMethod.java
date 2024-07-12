package com.example.springsecurity.lowleveldesignpattern.creational.factory;

public class FactoryMethod {
    public static void main(String[] args) {
        AnimalFactory factory = new DogFactory();
        Animal animal = factory.createAnimal();
        animal.speak();
    }
}

interface Animal{
    void speak();
}
class Dog implements Animal{
    @Override
    public void speak() {
        System.out.println("Dog barks");
    }
}
class Cat implements Animal{
    @Override
    public void speak() {
        System.out.println("Cat Meow");
    }
}

abstract class AnimalFactory{
    public abstract Animal createAnimal();
}

class DogFactory extends AnimalFactory{
    @Override
    public Animal createAnimal() {
        return new Dog();
    }
}

class CatFactory extends AnimalFactory{
    @Override
    public Animal createAnimal() {
        return new Cat();
    }
}