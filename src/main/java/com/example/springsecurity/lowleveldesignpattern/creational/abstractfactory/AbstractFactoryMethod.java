package com.example.springsecurity.lowleveldesignpattern.creational.abstractfactory;

public class AbstractFactoryMethod {
    public static void main(String[] args) {
        FurnitureFactory furnitureFactory = new ModernFurnitureFactory();
        furnitureFactory.createChair().sitOn();
        furnitureFactory.createSofa().lieOn();
        FurnitureFactory victorianFactory = new VictorianFurnitureFactory();
        victorianFactory.createSofa().lieOn();
        victorianFactory.createChair().sitOn();
    }
}
interface Chair{
    void sitOn();
}
interface Sofa{
    void lieOn();
}

class ModernChair implements Chair{
    @Override
    public void sitOn() {
        System.out.println("You are sitting on Modern Chair");
    }
}

class ModernSofa implements Sofa{
    @Override
    public void lieOn() {
        System.out.println("You are lying on Modern Sofa");
    }
}
class VictorianChair implements Chair{
    @Override
    public void sitOn() {
        System.out.println("You are sitting on Victorian Chair");
    }
}

class VictorianSofa implements Sofa{
    @Override
    public void lieOn() {
        System.out.println("You are lying on Victorian Chair");
    }
}

interface FurnitureFactory{
    Chair createChair();
    Sofa createSofa();
}
class ModernFurnitureFactory implements FurnitureFactory{
    @Override
    public Chair createChair() {
        return new ModernChair();
    }

    @Override
    public Sofa createSofa() {
        return new ModernSofa();
    }
}
class VictorianFurnitureFactory implements FurnitureFactory{
    @Override
    public Chair createChair() {
        return new VictorianChair();
    }

    @Override
    public Sofa createSofa() {
        return new VictorianSofa();
    }
}

