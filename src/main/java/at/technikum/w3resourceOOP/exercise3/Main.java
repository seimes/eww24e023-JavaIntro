package at.technikum.w3resourceOOP.exercise3;

public class Main {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(4.5, 6);
        
        System.out.println("Rectangle with width: " + rectangle.getWidth() + " and height: " + rectangle.getHeight());
        System.out.println("Area: " + rectangle.getArea() + " square meters");
        System.out.println("Perimeter: " + rectangle.getPerimeter() + " meters");
    }
}
