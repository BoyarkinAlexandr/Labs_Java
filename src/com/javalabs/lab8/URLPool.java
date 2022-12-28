package com.javalabs.lab8;


import java.util.*;

//Этот класс отвечает за многопоточную обработку пул ссылок

public class URLPool {

    public static int maxDepth;
    private LinkedList<URLDepthPair> rowURLs; //ожидающие ссылки
    public LinkedList<URLDepthPair> processedURLs;  //обработанные ссылки
    public ArrayList<String> seenURLs = new ArrayList<String>();  //просмотренные ссылки
    public int waitingThreads; //ожидающее потоки

    //конструктор
    public URLPool(int d) {
        waitingThreads = 0;
        rowURLs = new LinkedList<URLDepthPair>();
        processedURLs = new LinkedList<URLDepthPair>();
        maxDepth = d;
    }


    public synchronized int getWaitThreads() {
        return waitingThreads;
    }

    //Полученение текущего колчиства ожидающих ссылок
    public synchronized int size() {
        return rowURLs.size();
    }

    //Уменьшение колчиства ждущих потков
    public synchronized void decrimentWaitingThreads() {
        waitingThreads--;
    }

    //Добавление в пул ссылку
    public synchronized boolean put(URLDepthPair depthPair) {
        boolean added = false;

//Добавляение происходит так,
//чтобы сслыки не было в списке уже обработанных ссылок,
//а также глубина должна быть меньше максимальной
//При успешном добавлении все спящие потоки, ожидающие новых ссылок
//будут разбужены с помощью notify()
        if (depthPair.getDepth() < maxDepth && !processedURLs.contains(depthPair.getURL())) {
            rowURLs.addLast(depthPair);
            added = true;
            this.notify();
        } else {
            addSeenURL(depthPair);
        }

        return added;
    }

    //Получение из пула ссылки
    public synchronized URLDepthPair get() {

//Если сслыки есть, то пул без проблем выдаст одну из них
//Если пул пустой, то заствит обратившийся к нему поток ждать с помощью wait()
        URLDepthPair myDepthPair = null;
        if (rowURLs.size() == 0) {
            waitingThreads++;
            try {
                this.wait();
            }
            catch (InterruptedException e) {
                System.err.println("InterruptedException: " + e.getMessage());
                return null;
            }
        }

        if(waitingThreads>0)waitingThreads--;
        myDepthPair = rowURLs.pop();
        addSeenURL(myDepthPair);
        processedURLs.add(myDepthPair);
        return myDepthPair;
    }
    //Добавление ссылки в просмотренные
    private synchronized void addSeenURL(URLDepthPair dp) {
        if(!seenURLs.contains(dp.toString()))
            seenURLs.add(dp.toString());
    }

}