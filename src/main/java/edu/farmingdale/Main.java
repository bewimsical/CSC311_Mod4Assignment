package edu.farmingdale;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    /**
     * This method controls the program. It parses the weather data and provides a menu in the console for interacting
     * with the methods in the Main class.
     * @param args
     */
    public static void main(String[] args) {
        List<Day> weather = new ArrayList<>();
        FileReader fr;
        // Parse weather data (e.g., temperature, humidity, precipitation) from a CSV file.
        try {
            fr = new FileReader("weatherdata.csv");
            Scanner sc = new Scanner(fr);
            String[] headers_array = sc.nextLine().split(","); // splits data at comma
            Map<String, Integer> headers = new HashMap<>(); //maps headers to index
            for (int i = 0; i < headers_array.length; i++) {
                headers.put(headers_array[i].toLowerCase(), i);
            }
            while(sc.hasNextLine()){ //gets each line of data,
                String[] data = sc.nextLine().split(",");// splits it into an array at the comma
                String date = data[headers.get("date")];
                double temp = Double.parseDouble(data[headers.get("temperature")]);
                double hum = Double.parseDouble(data[headers.get("humidity")]);
                double precip = Double.parseDouble(data[headers.get("precipitation")]);
                Day d = new Day(date, temp, hum, precip);  //creates a day object
                weather.add(d); //adds object to list
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        }
        String[] monthVals = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        Map <String, String > months = new HashMap<>();
        for (int i = 1; i < 13; i++) {
            String s = i < 10 ? "0"+i : ""+i;
            months.put(s, monthVals[i-1]);
        }
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        String dayRegex = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
        String monthRegex = "^(0[1-9]|1[0-2])$";
        String welcome = """
                ========================================
                    Welcome to Weather Data Analyzer
                ========================================
                This application provides information about the weather for the year 2024
                """;
        String menu = """
                Please choose from the following menu options by entering a number:
                    (1) Get weather for a Day
                    (2) Get the temperature category for a Day
                    (3) Get the average temperature for a specific month
                    (4) Get days with temperatures above a given threshold
                    (5) Get the number of rainy days for a given month
                    (6) Get the number of rainy days in 2024
                    (0) Quit
       
                """;
        System.out.println(welcome);
        while (running){

            System.out.print(menu);
            String choice = sc.nextLine();
            switch(choice){
                case "1" -> {
                    String format = "mm-dd";
                    String date = validateDate(dayRegex, sc, format);
                    Day day = getDay(weather, date);
                    if (day != null){
                        printWeather(day);
                    }else System.out.printf("no weather data for 2024-%s", date);
                }
                case "2" ->{
                    String format = "mm-dd";
                    String date = validateDate(dayRegex, sc, format);
                    Day day = getDay(weather, date);
                    if (day != null){
                        System.out.printf("The temperature was %s on 2024-%s\n",getTemperatureCategory(day), date);
                    }else System.out.printf("no weather data for 2024-%s\n", date);
                }
                case "3" ->{
                    String format = "mm";
                    String month = validateDate(monthRegex, sc, format);
                    System.out.printf("The average temperature for the month %s is %.2f\n", month, getMonthAvgTemp(weather, month));
                }
                case "4" -> {
                    System.out.println("Please enter a temperature threshold value");
                    double threshold = 0;
                    boolean validating = true;
                    while (validating) {
                        try {
                            threshold = Double.parseDouble(sc.nextLine());
                            validating = false;
                        } catch (NumberFormatException e) {
                            System.out.println("invalid temperature. Temperature must be a digit");
                        }
                    }
                    List<Day> days = getDaysAboveThreshold(weather,threshold);
                    System.out.printf("The following days had a temperature above %s°F:\n", threshold);
                    for(Day d : days){
                        if (d != null) {
                            System.out.printf("%s: %.2f°F\n", d.date(), d.temperature());
                        }
                    }
                }
                case "5" -> {
                    String format = "mm";
                    String month = validateDate(monthRegex, sc, format);
                    System.out.printf("There were %s rainy days in %s\n", getRainyDayCount(weather, month), month);
                }
                case "6" -> {
                    System.out.printf("There were %s rainy days in 2024\n", getRainyDayCount(weather));
                }
                case "0" -> running = false;
                default -> System.out.println("invalid input");
            }
        }
        System.out.println("Thank you for using Weather Data Analyzer");



        for(Day d: weather){
            String month = "02";
            if(d.date().matches("(2024-"+month+"-\\d+)")) {
                printWeather(d);
            }
        }
        System.out.println(getMonthAvgTemp(weather, "02"));
        for(Day d: getDaysAboveThreshold(weather, 80)){
            System.out.printf("%s: %s\n", d.date(), d.temperature());
        }
        System.out.printf("There were %s rainy days this year", getRainyDayCount(weather, "04"));
    }

    /**
     * This method prints the temperature, humidity and precipitation for a day
     * @param d Day object that contains weather data for a specific day
     */
    public static void printWeather(Day d){
        String percent = "%";
        String text ="""
                    Here is the weather for: %s
                        Temperature: %s°F
                        Humidity: %s%s
                        Precipitation: %s in
                    """;

        System.out.printf((text) + "%n", d.date(), d.temperature(), d.humidity(),percent, d.precipitation());
    }

    /**
     * This method gets the average temperature for a specific month
     * @param data a list storing day objects
     * @param month the month as a string where "01" represents January and "12" represents December
     * @return the average temperature for all the days in the month
     */
    public static double getMonthAvgTemp(List<Day> data, String month){
        return data.stream()
                .filter(d -> d.date().matches("(2024-"+month+"-\\d+)"))
                .collect(Collectors.averagingDouble(Day::temperature));
    }

    /**
     * This method gets a list Days with temperatures above a given threshold
     * @param data a list of Day objects
     * @param threshold a double that represents the temperature
     * @return a list of days with a temperature value greater than the threshold value
     */
    public static List<Day> getDaysAboveThreshold(List<Day> data, double threshold){
        return  data.stream()
                .filter(d -> d.temperature() > threshold)
                .toList();
    }

    /**
     * This method returns the number of rainy days for a specific month
     * @param data a list of Day objects
     * @param month the month as a string where "01" represents January and "12" represents December
     * @return a long value storing the number of days with a precipitation value equal to 1.0
     */
    public static long getRainyDayCount(List<Day> data, String month){
        return data.stream()
                .filter(d -> d.precipitation() == 1.0 && d.date().matches("(2024-"+month+"-\\d+)"))
                .count();
    }

    /**
     * This method returns the number of rainy days for the provided list of Days
     * @param data a list of Day objects
     * @return a long value storing the number of days with a precipitation value equal to 1.0
     */
    public static long getRainyDayCount(List<Day> data){
        return data.stream()
                .filter(d -> d.precipitation() == 1.0)
                .count();
    }

    /**
     * This method categorizes the temperature as Hot, cold or warm
     * @param day a Day object
     * @return a String value "Hot" if the temperature value is greater than 80, "Cold" if the temperature value is
     * less than 60 and "Warm" for any other temperature values.
     */
    public static String getTemperatureCategory(Day day){
        double temp = day.temperature();
        switch (temp){
            case Double t when t > 80 -> {
                return "Hot";
            }
            case Double t when t < 60 -> {
                return "Cold";
            }
            default -> {
                return "Warm";
            }
        }
    }
    public static Day getDay(List<Day> data, String day){
        day = "2024-"+day;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).date().equals(day)) return data.get(i);
        }
        return null;
    }
    public static String validateDate(String regex, Scanner sc, String format){
        String example = format.equals("mm") ? "where January would be 01":"where January 1st would be 01-01";
        System.out.println("Please enter a date using the format " + format);
        String date = sc.nextLine();
        while(!date.matches(regex)){
            System.out.printf("ERROR! Invalid date format. \n%s is not a valid date. \nEnter a date using the format %s %s\n>>", date, format, example);
            date = sc.nextLine();
        }
        return date;
    }
}