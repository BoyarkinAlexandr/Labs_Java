package com.javalabs.lab2;

public class Point2d {
    private double xCoord; //координата X
    private double yCoord; //координата Y

    public Point2d(double x, double y) {   //Конструктор инициализации
        xCoord = x;
        yCoord = y;
    }

    public Point2d() {                //Конструктор по умолчанию
        this(0.0,0.0);
    }

    public double getX () {  //Возвращение координаты X
        return xCoord;
    }

    public double getY () {  //Возвращение координаты Y
        return yCoord;
    }

    public void setX ( double val) {  //Установка значений координаты X
        xCoord = val;
    }

    public void setY ( double val) {  //Установка значений координаты Y
        yCoord = val;}

    public boolean check (Point2d a,Point2d b) ////Сравнение двух объектов
    {
        if ((a.xCoord == b.xCoord) && (a.yCoord == b.yCoord))
            return true;
        else
            return false;
    }




}
