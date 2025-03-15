package edu.farmingdale;

/**
 * This record stores weather data for a specific day
 * @param date A string with the format yyyy-mm-dd
 * @param temperature a double storing the temperature in degrees Fahrenheit
 * @param humidity  a double storing the humidity as a percentage range: 0 - 100
 * @param precipitation  a double storing the chance of precipitation range: 0 - 1
 */
public record Day(String date, double temperature, double humidity, double precipitation) {
}
