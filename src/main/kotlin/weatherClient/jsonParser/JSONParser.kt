package weatherClient.jsonParser

import org.json.JSONObject
import weatherClient.pojo.CurrentWeatherPOJO
import weatherClient.pojo.OneDayForecastPOJO
import weatherClient.pojo.OneHourForecastPOJO

interface JSONParser {
    fun parseCurrentWeather(response: JSONObject): CurrentWeatherPOJO
    fun parseHourlyForecast(response: JSONObject): Array<OneHourForecastPOJO?>
    fun parseDailyForecast(response: JSONObject): Array<OneDayForecastPOJO?>
}