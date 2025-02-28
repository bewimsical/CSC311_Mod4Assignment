package edu.farmingdale;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Day> weather = new ArrayList<>();
        FileReader fr;
        try {
            fr = new FileReader("weatherdata.csv");
            Scanner sc = new Scanner(fr);
            String[] headers_array = sc.nextLine().split(",");
            Map<String, Integer> headers = new HashMap<>();
            for (int i = 0; i < headers_array.length; i++) {
                headers.put(headers_array[i].toLowerCase(), i);
            }
            while(sc.hasNextLine()){
                String[] data = sc.nextLine().split(",");
                String date = data[headers.get("date")];
                double temp = Double.parseDouble(data[headers.get("temperature")]);
                double hum = Double.parseDouble(data[headers.get("humidity")]);
                double precip = Double.parseDouble(data[headers.get("precipitation")]);
                Day d = new Day(date, temp, hum, precip);
                weather.add(d);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        }

        for(Day d: weather){
            printWeather(d);
        }
    }
    //this method prints the weather for a day
    public static void printWeather(Day d){
        String text ="""
                    Here is the weather for: %s
                        Temperature: %s degrees Fahrenheit
                        Humidity: %s
                        Precipitation: %s in
                    """;

        System.out.printf((text) + "%n", d.date(), String.valueOf(d.temperature()), String.valueOf(d.humidity()), String.valueOf(d.precipitation()));
    }
}