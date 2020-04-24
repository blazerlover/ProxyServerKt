package weatherClient.jsonParser

import org.json.JSONObject
import weatherClient.pojo.CurrentWeatherPOJO
import weatherClient.pojo.DailyForecastPOJO
import weatherClient.pojo.HourlyForecastPOJO

interface JSONParser {
    fun parseCurrentWeather(response: JSONObject): CurrentWeatherPOJO
    fun parseHourlyForecast(response: JSONObject): HourlyForecastPOJO
    fun parseDailyForecast(response: JSONObject): DailyForecastPOJO
}