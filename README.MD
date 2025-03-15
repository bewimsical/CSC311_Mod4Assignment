# Weather Data Analyzer

## Overview
The **Weather Data Analyzer** is a Java-based console application that parses weather data from a CSV file and allows users to interactively retrieve weather information for the year 2024. The program provides a menu-driven interface for users to query various weather-related statistics.

## Features
- Retrieve weather data for a specific day.
- Categorize a day's temperature as "Hot," "Cold," or "Warm."
- Calculate the average temperature for a given month.
- Identify days with temperatures above a user-specified threshold.
- Count the number of rainy days in a specific month.
- Count the total number of rainy days in 2024.

## Installation and Setup
### Prerequisites
1. Ensure you have **Java 17+** installed.
2. Install **Maven** (Apache Maven 3.6+ recommended).

### Build and Run
1. Clone the repository or download the source code.
2. Navigate to the project root directory.
3. Build the project using Maven:
   ```sh
   mvn clean install
   ```
4. Run the application with:
   ```sh
   mvn exec:java -Dexec.mainClass="Main"
   ```

## Usage
Upon running the program, a menu will be displayed with the following options:

```
========================================
    Welcome to Weather Data Analyzer
========================================
This application provides information about the weather for the year 2024

Please choose from the following menu options by entering a number:
    (1) Get weather for a Day
    (2) Get the temperature category for a Day
    (3) Get the average temperature for a specific month
    (4) Get days with temperatures above a given threshold
    (5) Get the number of rainy days for a given month
    (6) Get the number of rainy days in 2024
    (0) Quit
```

The user enters a choice, and the program prompts for the necessary input (date, temperature threshold, or month).

### Example Usage
- Selecting **option 1** prompts the user to enter a date (format: `mm-dd`).
- Selecting **option 3** prompts the user to enter a month (format: `mm`).
- Selecting **option 4** prompts the user to enter a temperature threshold.

## Data Format
The program reads weather data from a **CSV file** (`weatherdata.csv`) with the following columns:
```
Date, Temperature, Humidity, Precipitation
2024-01-01, 32.5, 60.2, 0.0
2024-01-02, 45.1, 55.0, 1.0
...
```

## Methods
- `printWeather(Day d)`: Displays the temperature, humidity, and precipitation for a given day.
- `getMonthAvgTemp(List<Day> data, String month)`: Returns the average temperature for a given month.
- `getDaysAboveThreshold(List<Day> data, double threshold)`: Returns a list of days with temperatures above the specified threshold.
- `getRainyDayCount(List<Day> data, String month)`: Returns the count of rainy days in a specified month.
- `getRainyDayCount(List<Day> data)`: Returns the total number of rainy days in 2024.
- `getTemperatureCategory(Day day)`: Categorizes the temperature as "Hot," "Cold," or "Warm."
- `getDay(List<Day> data, String day)`: Retrieves the weather data for a given day.
- `validateDate(String regex, Scanner sc, String format)`: Ensures the user enters a valid date format.

## Dependencies
- Java Standard Library (`java.util`, `java.io`)
- Maven for dependency management and build automation

## Author
Kathleen Wims

