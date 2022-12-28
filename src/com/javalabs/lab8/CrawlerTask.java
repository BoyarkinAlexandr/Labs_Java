package com.javalabs.lab8;

import java.util.*;

//через интерфейс Runnable реализуется многопоточность
//также могопоточность можно реализовать, наследуя класс Thread
public class CrawlerTask implements Runnable {

    //Инициализация поля для заданной пары
    public URLDepthPair depthPair;

    //Инициализация поля для ссылки
    public URLPool myPool;


    public CrawlerTask(URLPool pool) {
        myPool = pool;
    }

    //Основной метод класса CrawlerTask
    //выполняется поиск ссылок на странице в отдельном потоке
    public void run() {
        //Здесь происзодит получение ссылки из пула
        //если там нет ссылок, то поток будет ждать, пока один из потоков их туда не положит
        depthPair = myPool.get();

        //Глубина
        int myDepth = depthPair.getDepth();

        // Достать все ссылки со страницы
        LinkedList<String> linksList = new LinkedList<String>();
        linksList = Crawler.getAllLinks(depthPair);

        // Перебирает все ссылки с сайта
        for (int i=0;i<linksList.size();i++) {
            String newURL = linksList.get(i);

            // Создает новую пару для каждой ссылки с уровнем глубины 1 больше предыдущей
            URLDepthPair newDepthPair = new URLDepthPair(newURL, myDepth + 1);
            myPool.put(newDepthPair);
        }
    }
}