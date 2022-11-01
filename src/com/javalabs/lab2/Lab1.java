package com.javalabs.lab2;

import java.util.Scanner;

public class Lab1 extends Point3d {
    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        Point3d coord1 = new Point3d();
        Point3d coord2 = new Point3d();
        Point3d coord3 = new Point3d();
        System.out.println("Введите координаты Х для 1 точки:");
        coord1.setX(scn.nextDouble());
        System.out.println("Введите координаты Y для 1 точки:");
        coord1.setY(scn.nextDouble());
        System.out.println("Введите координаты Z для 1 точки:");
        coord1.setZ2(scn.nextDouble());

        System.out.println("Введите координаты Х для 2 точки:");
        coord2.setX(scn.nextDouble());
        System.out.println("Введите координаты Y для 2 точки:");
        coord2.setY(scn.nextDouble());
        System.out.println("Введите координаты Z для 2 точки:");
        coord2.setZ2(scn.nextDouble());

        System.out.println("Введите координаты Х для 3 точки:");
        coord3.setX(scn.nextDouble());
        System.out.println("Введите координаты Y для 3 точки:");
        coord3.setY(scn.nextDouble());
        System.out.println("Введите координаты Z для 3 точки:");
        coord3.setZ2(scn.nextDouble());
        scn.close();
        System.out.println(String.format("%.2f", coord1.distanceTo(coord1, coord2)));
        if (ComparingCoord(coord1,coord2) == false)
            System.out.println(computeArea(coord1,coord2,coord3));
        else
            System.out.println("Координаты одинаковы");

    }


    public static double computeArea(Point3d obj1,Point3d obj2,Point3d obj3) //Площадь треугольника по формуле Герона
    {
        double a = Math.sqrt(Math.pow(obj1.getX()-obj2.getX(),2) + Math.pow(obj1.getZ2()-obj2.getZ2(),2) + Math.pow(obj1.getY()-obj2.getY(),2));
        double b = Math.sqrt(Math.pow(obj2.getX()-obj3.getX(),2) + Math.pow(obj2.getZ2()-obj3.getZ2(),2) + Math.pow(obj2.getY()-obj3.getY(),2));
        double c = Math.sqrt(Math.pow(obj1.getX()-obj3.getX(),2) + Math.pow(obj1.getZ2()-obj3.getZ2(),2) + Math.pow(obj1.getY()-obj3.getY(),2));
        double p = (a + b + c)/2;
        return Math.sqrt(p*(p-a)*(p-b)*(p-c));
    }
}
