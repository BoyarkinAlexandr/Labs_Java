package com.javalabs.lab2;


public class Point3d extends Point2d {

    /*public static void main(String[] args) {
        Point3d cord1 = new Point3d(1, 2, 3);
        Point3d cord2 = new Point3d(1, 2, 3);
        Point3d cord3 = new Point3d(11.88, 22.56, 16.3);
        String str = String.valueOf(ComparingCoord(cord1,cord2));
        System.out.println(str);
        System.out.println("Растояние между двумя точками равно = " + String.format("%.2f", cord1.distanceTo(cord3)));
    }*/


    private double z_2Coord; //координата Z



    public Point3d (double x, double y, double z) {   //Конструктор инициализации
        super(x, y);
        z_2Coord = z;
    }

    public Point3d(){                //Конструктор по умолчанию
        this(0.0,0.0,0.0);
    }



    public double getZ2 () {  //Возвращение координаты Z
        return z_2Coord;
    }


    public void setZ2 ( double val) {  //Установка значений координаты Z
        z_2Coord = val;
    }

    public static boolean ComparingCoord(Point3d obj1, Point3d obj2)  //Сравнение двух объектов
    {
        if ((obj1.getX() == obj2.getX()) || (obj1.getY() == obj2.getZ2()) || (obj1.getZ2() == obj2.getZ2()))
            return true;
        else
            return false;
    }

    public double distanceTo(Point3d obj1,Point3d obj2)  //Вычисление расстояния между двумя точками
    {
        return Math.sqrt(Math.pow(obj2.getX() - obj1.getX(), 2)
                + Math.pow(obj2.getY() - obj1.getY(), 2)
                + Math.pow(obj2.getZ2() - obj1.getZ2(), 2));
    }


}

