package com.javalabs.lab4;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
/*
Этот класс позволит исследовать
различные области фрактала, путем его создания, отображения через
графический интерфейс Swing и обработки событий, вызванных
взаимодействием приложения с пользователем.
*/
public class FractalExplorer {
    // целочисденный размер  окна - это ширина и высота отображения в пикселях
    private int displaySize;


    //Ссылка JImageDisplay, для обновления отображения в разных методах в процессе вычисления фрактала
    private JImageDisplay display;


    /*
    Объект FractalGenerator, использующий ссылку на базовый класс для отображения
    других типов фракталов в будущем. (для каждого типа фракталов)
    */
    private FractalGenerator fractal;


    /*
    Объект Rectangle2D.Double, диапазона комплексной
    плоскости
     */
    private Rectangle2D.Double range;


    /*
     Конструктор, который принимает размер дисплея, сохраняет его и
     инициализирует объекты диапазона и фрактал-генератора.
     */
    public FractalExplorer(int size) {
        //Сохраняет размер дисплея
        displaySize = size;
        //Инициализирует фрактальный генератор и объекты диапазона
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }
    /*
    Этот метод инициализирует графический интерфейс Swing с помощью JFrame, содержащий
    JImageDisplay объект и кнопку для сброса дисплея.
    */
    public void createAndShowGUI()
    {
        //Устанавливаем фрейм на использование java.awt.BorderLayout для его содержимого
        display.setLayout(new BorderLayout()); // разбивает окно на 4 части
        JFrame myframe = new JFrame("Fractal Explorer");

        //Добавляем объект отображения изображения центр
        myframe.add(display, BorderLayout.CENTER);

        //Создаем кнопку сброса
        JButton resetButton = new JButton("Reset Display");

        //Экземпляр ResetHandler на кнопке сброса
        ResetHandler handler = new ResetHandler();
        resetButton.addActionListener(handler);

        //Добавляем кнопку сброса в позицию BorderLayout.SOUTH
        myframe.add(resetButton, BorderLayout.SOUTH);

        //Экземпляр MouseHandler для обработки нажатия мыши
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        //Устанавливает операцию закрытия фрейма по умолчанию на "exit"
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Разместить содержимое фрейма так, чтобы оно было видно, и запретить изменение размера окна
        myframe.setTitle("this is my fractal");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        myframe.setBounds(dimension.width/2 - 300,dimension.height/2 - 300, 600, 600);
        myframe.pack(); //окно подстраивается под размер элементов
        myframe.setVisible(true);
        myframe.setResizable(false);
    }
    /*
    Приватный вспомогательный метод для отображения фрактала. Этот метод
    должен циклически проходить через каждый пиксель в отображении (т.е.
    значения x и y будут меняться от 0 до размера отображения)
    и вычислять количество итераций для соответствующих координат в области
    отображения фракталов.
    */

    // метод отрисовки и изображения фрактала
    private void drawFractal()
    {
        //проходим через все пиксили на дисплее
        for (int x=0; x<displaySize; x++){
            for (int y=0; y<displaySize; y++){
                /*
                Находим соответствующие координаты xCoord и yCoord
                в области отображения фрактала.
                */
                double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);
                /*
                Вычисляем количество итераций для координат в
                области отображения фрактала.
                */
                int iteration = fractal.numIterations(xCoord, yCoord);
                //Если количество итераций равно -1, установить черный пиксель
                if (iteration == -1){
                    display.drawPixel(x, y, 0);
                } else {
                    //Выберем значение оттенка на основе числа итераций
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    //Обновление дисплея с цветами каждого пикселя.
                    display.drawPixel(x, y, rgbColor);
                }
            }
        }
        /*
        Когда все пиксели будут нарисованы, перекрасить JImageDisplay в соответствии с
        текущим изображением.
        */
        display.repaint(); //перерисовка компонента
    }
    //Внутренний класс для обработки событий ActionListener от кнопки сброса
    private class ResetHandler implements ActionListener //считывает события в окне
    {
        // внутренний класс для обработки событий
        public void actionPerformed(ActionEvent e)
        {
            //инициализация и отрисовка фрактала
            fractal.getInitialRange(range);
            drawFractal();
        }
    }

    //Обработчик событий MouseListener с дисплея
    private class MouseHandler extends MouseAdapter
    {
        //Когда обработчик получает событие щелчка мыши,
        // он отображает пиксельные координаты щелчка в область отображаемого фрактала,
        // а затем вызывает метод recenterAndZoomRange() генератора с координатами,
        // которые были нажаты, и масштабом 0,5.

        @Override //переопределяем метод базового класса
        public void mouseClicked(MouseEvent e)
        {
            // х координата клика
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x,
                    range.x + range.width, displaySize, x);

            // у координата клика
            int y = e.getY();
            double yCoord = fractal.getCoord(range.y,
                    range.y + range.height, displaySize, y);

            /*
            Вызов метода генератора RecenterAndZoomRange () с
            координатами, по которым нажали, и масштабом 0,5.
            */
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

            // перерисовка фрактала после изменения отображаемой области
            drawFractal();
        }
    }
    /*
    Статический метод main () для запуска FractalExplorer. Создаем экземпляр и рисуем фрактал. Инициализирует новый
    экземпляр FractalExplorer с размером дисплея 600, вызывает
    createAndShowGUI (), а затем вызывает
    drawFractal () ,чтобы увидеть исходный вид.
    */
    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}
