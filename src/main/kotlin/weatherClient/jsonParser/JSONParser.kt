package weatherClient.jsonParser

import org.json.JSONObject
import weatherClient.pojo.CurrentWeatherPOJO
import weatherClient.pojo.DayForecastPOJO
import weatherClient.pojo.HourForecastPOJO

interface JSONParser {
    fun parseCurrentWeather(response: JSONObject): CurrentWeatherPOJO
    fun parseHourlyForecast(response: JSONObject): Array<HourForecastPOJO?>
    fun parseDailyForecast(response: JSONObject): Array<DayForecastPOJO?>
}