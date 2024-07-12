package com.example.springsecurity.lowleveldesignpattern.creational.factory;

interface Shape {
    void draw();
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Draw Circle");
    }
}

class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Draw Square");
    }
}
abstract class ShapesFactory  {
     public abstract Shape createShapes();
}
class CircleFactory extends ShapesFactory{
    @Override
    public Shape createShapes() {
        return new Circle();
    }
}
class SquareFactory extends ShapesFactory{
    @Override
    public Shape createShapes() {
        return new Square();
    }
}

class ShapeFactory {
    public static void main(String[] args) {
        ShapesFactory shapesFactory = new CircleFactory();
        shapesFactory.createShapes();
    }
}

