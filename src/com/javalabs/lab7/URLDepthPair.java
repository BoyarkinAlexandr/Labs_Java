package com.javalabs.lab7;

import java.net.MalformedURLException;
import java.net.URL;
//Класс для хранения ссылки и ее глубины
public class URLDepthPair {
    private  String url;
    private int depth;

    public URLDepthPair(String URL, int dep) {
        url = URL;
        depth = dep;
    }
    public String getURL() { //Возвращает объект класса типа URL, то есть полный путь до сайта
        return url;
    }
    public int getDepth() { //Возвращает глубину сайта, относительно сайта введёного пользователем
        return depth;
    }

    public String toString() { //Возвращает строку состаящую из адреса сайта и его глубины
        return "<URL href=\"" + url  + "\" depth=\"" + depth + "\" \\>";
    }

}