package weatherClient.pojo

class DayForecastPOJO(val weather: Weather,
                      val date: Long,
                      val maxTemp: Double,
                      val minTemp: Double,
                      val maxTempFeelLike: Double,
                      val minTempFeelLike: Double,
                      val pressure: Double,
                      val humidity: Double,
                      val windSpeed: Double,
                      val windDir: String,
                      val pop: Int)