package weatherClient.pojo

class DailyForecastPOJO(val dailyForecasts: Array<DailyForecast?>) {

    class DailyForecast(val weather: Weather, val date: Long, val temp: Double, val pressure: Double, val humidity: Double, val windSpeed: Double)

}