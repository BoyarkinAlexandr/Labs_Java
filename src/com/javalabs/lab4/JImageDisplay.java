package com.javalabs.lab4;


import javax.swing.*; //библиотека для создания граф.интерфейс
import java.awt.image.*;
import java.awt.*;

public class JImageDisplay extends JComponent //класс JImageDisplay, производный от  javax.swing.JComponent//
{

    private BufferedImage displayImage;  // Управляет изображением, содержимое которого мы можем записывать

    //инициализирую объект класса BufferedImage с данными характеристиками
    //длинны и высоты, а также с типом изображения и типом изображения TYPE_INT_RGB. Тип определяет, как цвета каждого пикселя будут
    //представлены в изображении; значение TYPE_INT_RGB обозначает, что
    //красные, зеленые и синие компоненты имеют по 8 битов, представленные в
    //формате int в указанном порядке
    public JImageDisplay(int width, int height) {
        displayImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        /* Конструктор также должен вызвать метод setPreferredSize()
     родительского класса метод с указанной шириной и высотой. Таким образом,
     когда компонент будет включен в пользовательский интерфейс, он
     отобразит на экране все изображение.
     */
        Dimension imageDimension = new Dimension(width, height); // создаю объект класса Dimension
        // с данными характеристиками,чтобы в дальнейшем передатm родительскому классу
        super.setPreferredSize(imageDimension);}
    /* Передаём ширину и высоту в объект Dimension. Компонент отобразит всё изображение */


    // метод для отрисовки
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(displayImage, 0, 0, displayImage.getWidth(), displayImage.getHeight(), null);//Рисую изображение
    }

    //метод устанавливает все пиксели  изображения в черный цвет
    public void clearImage()
    {
        /*int[] blankArray = new int[getWidth() * getHeight()];
        displayImage.setRGB(0, 0, getWidth(), getHeight(), blankArray, 0, 1);*/
        for(int j = 0 ; j < displayImage.getHeight() ; ++j)
        {
            for(int i = 0 ; i < displayImage.getWidth() ; ++i)
            {
                this.drawPixel(i, j, 0);
            }
        }
    }
    // Устанавливает пиксель определенного цвета
    public void drawPixel(int x, int y, int Color)
    {
        displayImage.setRGB(x, y, Color);
    }

    public BufferedImage getImage() {
        return displayImage;
    }
}
