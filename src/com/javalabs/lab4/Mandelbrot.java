package com.javalabs.lab4;


import java.awt.geom.Rectangle2D;
//Этот класс является подклассом FractalGenerator. Он используется для вычисления Фрактал Мандельброта
public class Mandelbrot extends FractalGenerator{
    public static final int MAX_ITERATIONS = 2000;

    //     Этот метод должен установить начальный диапазон в (-2 - 1.5i) - (1 + 1.5i). Т.е. значения x
    //     и y будут равны -2 и -1.5 соответственно, а ширина и высота будут равны 3.

    public void getInitialRange (Rectangle2D.Double range){
        range.x=-2;
        range.y=-1.5;
        range.width=3;
        range.height=3;
    }
    /**
    Этот метод реализует итерационную функцию для фрактала Мандельброта.
    Требуется два дабла для действительной и мнимой частей комплекса.
    Вычисляется количество итераций для соответствующей координаты.
    */
    public int numIterations(double x, double y)
    {
        //Начать итерацию с нуля
        int iteration = 0;
        //Инициализация действительной и мнимой
        double zreal = 0;
        double zimaginary = 0;

        /**
         * Для фрактала Мандельброта функция z_n = (z_ (n-1))^2 + c,
         *  где все значения являются комплексными числами, z_0 = 0,
         *  и c-конкретная точка фрактала, которую мы показываем.
         *  Это вычисление повторяется до тех пор, пока либо  |z| > 2
         *  или до тех пор, пока число итераций не достигнет максимального значения, например 2000
         */
        while (iteration < MAX_ITERATIONS &&
                zreal * zreal + zimaginary * zimaginary < 4)
        {
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginaryUpdated = 2 * zreal * zimaginary + y;
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
    public String toString() {
        return "Mandelbrot";
    }
}
