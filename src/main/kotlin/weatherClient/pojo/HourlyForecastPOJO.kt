package weatherClient.pojo

class HourlyForecastPOJO(val hourlyForecasts: Array<HourlyForecast?>) {

    class HourlyForecast(val weather: Weather, val pod: String, val date: Long, val temp: Double, val pressure: Double, val humidity: Double, val windSpeed: Double)

}