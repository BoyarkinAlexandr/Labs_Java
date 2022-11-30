package com.javalabs.lab5;

import com.javalabs.lab4.FractalGenerator;

import java.awt.geom.Rectangle2D;
//Этот класс является подклассом FractalGenerator. Он используется для вычисления Фрактала
public class BurningShip extends FractalGenerator
{
    public static final int MAX_ITERATIONS = 2000;
    /*
     метод позволяет генератору
     фракталов определить наиболее «интересную» область комплексной плоскости
     для конкретного фрактала. Методу в качестве
     аргумента передается прямоугольный объект, и метод должен изменить поля
     прямоугольника для отображения правильного начального диапазона для
     фрактала. Этот метод
     должен установить начальный диапазон в (-2 - 1.5i) - (1 + 1.5i). Т.е. значения x
     и y будут равны -2 и -1.5 соответственно, а ширина и высота будут равны 3.
     */
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }
    /*
    Этот метод реализует итерационную функцию для фрактала Мандельброта.
    Требуется два дабла для действительной и мнимой частей комплекса.
    Вычисляется количество итераций для соответствующей координаты.
    */
    public int numIterations(double x, double y)
    {
        //Начать итерацию с нуля
        int iteration = 0;
        //Инициализация zreal и zimaginary
        double zreal = 0;
        double zimaginary = 0;
        /*
        Вычислить Zn = Zn-1 ^ 2 + c, где значения представляют собой комплексные числа, представленные
        по zreal и zimaginary, Z0 = 0, а c - особая точка в
        фрактале, который мы отображаем (заданный x и y). Это повторяется
        до Z ^ 2> 4 (абсолютное значение Z больше 2) или максимум
        достигнуто количество итераций.
        */
        while (iteration < MAX_ITERATIONS &&
                zreal * zreal + zimaginary * zimaginary < 4)
        {
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginaryUpdated = 2 * Math.abs(zreal) * Math.abs(zimaginary) + y;
            zreal = zrealUpdated;
            zimaginary = zimaginaryUpdated;
            iteration += 1;
        }
        /*
        В случае, если алгоритм дошел до значения MAX_ITERATIONS нужно
        вернуть -1, чтобы показать, что точка не выходит за границы.
        */
        if (iteration == MAX_ITERATIONS)
        {
            return -1;
        }
        return iteration;
    }
    //Возврат названия фрактала
    public String toString() {
        return "Burning Ship";
    }
}
