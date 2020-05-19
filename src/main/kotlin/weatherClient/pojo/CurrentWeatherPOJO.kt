package weatherClient.pojo

class CurrentWeatherPOJO(val weather: Weather,
                         val pod: String,
                         val temp: Double,
                         val feelLike: Double,
                         val pressure: Double,
                         val humidity: Double,
                         val windSpeed: Double,
                         val windDir: String,
                         val uvIndex: Int,
                         val visibility: Int,
                         val dewPoint: Double)