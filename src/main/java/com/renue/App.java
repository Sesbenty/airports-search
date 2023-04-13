package com.renue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


//Запуск приложения
//cd target
//java -jar .\airports-search-1.0-SNAPSHOT.jar
public class App {

    public static void main(String[] args) {
        var a = new AirportsStore();
        App app = new App();
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите параметры запроса");
        Query query = new Query();
        query.BuildQuery(sc.nextLine());

        InputStream stream = app.getFileAsIOStream("airports.csv");
        try (InputStreamReader isr = new InputStreamReader(stream);
             BufferedReader br = new BufferedReader(isr)) {
            CSVParser parser = new CSVParser();
            String line;

            while ((line = br.readLine()) != null) {
                String[] arr = parser.Parse(line);
                if (query.Execute(arr)) {
                    a.Add(arr[1], line);
                }
            }

            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        a.Sort();

        System.out.println("Введите начало имени аэропорта:");
        String airName = sc.nextLine();
        while (!airName.equals("!quit")) {


            long timeStart = System.currentTimeMillis(); //Старт замера времени
            var p = a.FindStartWith(airName);
            int numberString = p.size();
            for (AirportsStore.Pair item:p) {
                System.out.printf("\"%s\" [%s]\n",item.name, item.csv);
            }
            long busseTime = System.currentTimeMillis() - timeStart; //Конец замера


            System.out.printf("Количество найденных строк: %d Время, затраченное на поиск: %d мс\n", numberString, busseTime);
            System.out.println("Введите начало имени аэропорта:");
            airName = sc.nextLine();
        }
    }

    private InputStream getFileAsIOStream(final String fileName) {
        InputStream ioStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    private void ReadTextInFileIOStream(InputStream stream) {
        try (InputStreamReader isr = new InputStreamReader(stream);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
