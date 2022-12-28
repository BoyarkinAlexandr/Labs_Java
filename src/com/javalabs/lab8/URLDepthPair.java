package com.javalabs.lab8;




import java.net.*;

//Класс для хранения ссылки и ее глубины
public class URLDepthPair {
    //Инициализация текущего URL-адреса и текущей глубины
    private String presentURL = "";
    private int presentDepth;

    //Конструктор, который устанавливает входные данные
    public URLDepthPair(String URL, int depth) {
        presentURL = URL;
        presentDepth = depth;
    }
    URLDepthPair(String URL){
        presentURL = URL;
        presentDepth = 0;
    }


    //Возвращает текущий URL
    public String getURL() {
        return presentURL;
    }
    //Вовзращает текущее значение Depth (глубины)
    public int getDepth() {
        return presentDepth;
    }



    //Возвращает текущий URL-адрес и текущую глубину в
//формате string
    public String toString() {
        String stringDepth = Integer.toString(presentDepth);
        return stringDepth + ' ' + presentURL;
    }

    //достать exceptionDocPath из ссылки стандартной библиотекой
// Исключение для URL-адресов,
// которые не начинаются с «http: //»
    public String exceptionDocPath() {
        try {
            URL newURL = new URL(presentURL);
            return newURL.getPath();
        }
        catch (MalformedURLException e) {
            System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }


    //Достать имя хоста из ссылки стандартной библиотекой
// Метод, который возвращает веб-хост текущего URL-адреса.
    public String exceptionHost() {
        try {
            URL url = new URL(presentURL);
            return url.getHost();
        }
        catch (MalformedURLException e) {
            System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }


    }
}