package com.javalabs.lab8;


import java.util.*;
import java.io.*;
import java.net.*;

public class Crawler {

    static int numThreads = 10; //максимальное количсетво одновременно работающих потоков
    public static void main(String[] args1) {


        String args[] = new String[2];
        Scanner scanstr = new Scanner(System.in);
        System.out.println("Enter URL: ");
        args[0] = scanstr.nextLine();
        System.out.println("Enter the depth: ");
        args[1] = scanstr.nextLine();
        // Переменная для текущей глубины.
        int depth = 0;

        // Проверяет правильность длины ввода.
        if (args.length != 2) {
            System.out.println("usage: java Crawler <URL> <depth>");
            System.exit(1);
        }
        // Если ввод правильный, продолжаем.
        else {
            try {
                // Преобразование строкового аргумента в целочисленное значение.
                depth = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException nfe) {
                // Второй аргумент не является допустимым целым числом. Остановить и
                //вывести сообщение.
                System.out.println("usage: java Crawler <URL> <depth>");
                System.exit(1);
            }
        }
        //Изначальная ссылка
        URLDepthPair nowDepthPair = new URLDepthPair(args[0]);
        //Инициализация списка просмотренных ссылок
        ArrayList<String> seenURLs = new ArrayList<String>();

        //Добавления изначальное ссылки в список просмотренных
        seenURLs.add(nowDepthPair.getURL());

        //Глубина поиска
        URLPool pool = new URLPool(depth);

        //Добавление первой пары в пул
        pool.put(nowDepthPair);

        //Начальное количество активных потоков.
        //Нужно для поддержания numThreads работающих потоков
        int activeThreads = Thread.activeCount();

        //Здесь происзодит запуск каждого потока.
        //Цикл работает, пока количество ждущих потоков не сравняется с максимальных количеством потоков.
        //Это условие гарантирует то, что поиск ссылок закончен.
        //То есть, если ни один из потоков не работает и пул пустой, то поиск закончен.
        while (pool.getWaitThreads() != numThreads) {

            if (Thread.activeCount() - activeThreads < numThreads) {
                //Создание нового экземпляра класса CrawlerTask
                //Запуск нового потока
                CrawlerTask crawler = new CrawlerTask(pool);
                new Thread(crawler).start();
            }
            //Иначе спим, а потом пробуем заново
            else {
                try {
                    Thread.sleep(100); // 0.1 секунда
                }
                catch (InterruptedException ie) {
                    System.out.println("Caught unexpected: InterruptedException, ignoring...");
                }

            }
        }
        //После завершения поска в консоль выводит все найденные ссылки
        for(String s : pool.seenURLs) {
            System.out.println(s);
        }

        System.out.println(pool.seenURLs.size());
        System.exit(0);

    }

            //Метод для поиска сссылок на странице
        //Отправляет запрос на сервер, ждет ответа
        //Потом по ключевым символам вытаскивает ссылки со страницы
        //Ключевые символы - тэг ссылки HTML и кавычка, закрывающая его
    public static LinkedList<String> getAllLinks(URLDepthPair myDepthPair) {


        LinkedList<String> URLs = new LinkedList<String>(); //Массив со ссылками на странице

        Socket socket; // Инициализация сокета


        try {
            socket = new Socket(myDepthPair.exceptionHost(), 80);//Создание пары: хост и порт
        }

        catch (UnknownHostException e) {
            System.err.println("UnknownHostException: " + e.getMessage());//Ошибка UnknownHostException:
            return URLs;
        }

        catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());//Ошибка IOException
            return URLs;
        }

//После установки соединения с сервером готовмися выполнить запрос
//Также устанавливаем таймаут, чтобы не пришлось долго ждать ответа
//(это тормозит поток)
        try {
            socket.setSoTimeout(10000);
        }
        catch (SocketException exc) {
            System.err.println("SocketException: " + exc.getMessage());
            return URLs;
        }

        //Определяем вебхост и путь до документа, чтобы выоплнить запрос
        String docPath = myDepthPair.exceptionDocPath();
        String webHost = myDepthPair.exceptionHost();

        //Инициализация OutputStream
        OutputStream getOutputStream;

        //Поток вывода данных, через него реализуется отправка запроса
        try {
            getOutputStream = socket.getOutputStream();
        }
        catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            return URLs;
        }

        //Формирование http запроса на сервер
        //true значит, что файл будет закрыт автоматически
        PrintWriter req = new PrintWriter(getOutputStream, true);
        req.println("GET " + docPath + " HTTP/1.1");
        req.println("Host: " + webHost);
        req.println("Connection: close");
        req.println();

        //Дальше следует обработка входящих данных с сервера
        //Реализовано с использованием inputStream
        InputStream inStream;
        try {
            inStream = socket.getInputStream();
        }
        catch (IOException e){
            System.err.println("IOException: " + e.getMessage());
            return URLs;
        }

        //В buffReader хранится ответа сервера и оттуда происходит чтение
        InputStreamReader inStreamReader = new InputStreamReader(inStream);
        BufferedReader BuffReader = new BufferedReader(inStreamReader);

        //Обработка ответа будет проходить до конца, пока не закончится текст
        while (true) {
        //Раз за разом читаем строку
            String line;
            try {
                line = BuffReader.readLine();
            }
            catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
                return URLs;
            }
        //Если строка пустая, то это конец ответа
            if (line == null)
                break;
        //Инициализация переменных для отбора ссылок
            int beginIndex = 0; //индекс начала ссылки
            int endIndex = 0; //индекс конца ссылки
            int index = 0; //текущий индекс

        //Поиск всех ссылок в строке проходит пока они не закончатся
            while (true) {
        //так начинается и заканчивается ссылка в html коде
                String START_URL = "a href=\"";
                String END_URL = "\"";

                //Определение начала ссылки, начиная с текущей
                //Если больше нет - закончить
                index = line.indexOf(START_URL, index);
                if (index == -1)
                    break;

                //Определение начала и конца ссылки
                index += START_URL.length();
                beginIndex = index;

                endIndex = line.indexOf(END_URL, index);
                index = endIndex;

                //Здесь достается ссылка из текста и определяется, что с ней делать
                //Либо игнорировать, либо добавить в список ссылок
                try {
                    String newLink = line.substring(beginIndex, endIndex);

                    //Если ссылка начинается с протокола, то всё хорошо
                    if(newLink.startsWith("http")) {

                    }else if(newLink.startsWith("tel")) { //Если с tel, то игнорируем
                        continue;
                    }else {
                        //Иначе - собираем ссылку до полной и добавляем
                        if(newLink.startsWith("/"))
                            newLink = "http://"+webHost+""+newLink;
                        else
                            newLink = "http://"+webHost+"/"+newLink;

                    }
                    //Если ссылка уже есть, то пропускаем
                    if(URLs.contains(newLink))
                        continue;
                    //Добавляем
                    URLs.add(newLink);

                }catch(Exception e) {
                    System.err.println("error while substringing: "+beginIndex + " " + endIndex);
                    break;
                }

            }

        }
        return URLs;
    }
}