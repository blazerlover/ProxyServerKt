package weatherClient.pojo

class HourForecastPOJO(val weather: Weather,
                       val pod: String,
                       val date: Long,
                       val temp: Double,
                       val feelLike: Double,
                       val pressure: Double,
                       val humidity: Double,
                       val windSpeed: Double)