package weatherClient.jsonParser

import org.json.JSONObject
import weatherClient.pojo.CurrentWeatherPOJO
import weatherClient.pojo.OneHourForecastPOJO
import weatherClient.pojo.OneDayForecastPOJO
import weatherClient.pojo.Weather
import weatherServer.*

class JSONParserWeatherbit : JSONParser {
    override fun parseCurrentWeather(response: JSONObject): CurrentWeatherPOJO {
        val jsonObject = response.getJSONArray(PARSER_KEY_DATA).getJSONObject(0)
        val weather = parseWeatherState(jsonObject.getJSONObject(PARSER_KEY_WEATHER))
        val pod = jsonObject.getString(PARSER_KEY_POD)
        val temp = jsonObject.getDouble(PARSER_KEY_TEMP)
        val feelLike = jsonObject.getDouble(PARSER_KEY_APP_TEMP)
        val pressure = jsonObject.getDouble(PARSER_KEY_PRES)
        val humidity = jsonObject.getDouble(PARSER_KEY_RH)
        val windSpeed = jsonObject.getDouble(PARSER_KEY_WIND_SPD)
        return CurrentWeatherPOJO(weather, pod, temp, feelLike, pressure, humidity, windSpeed)
    }

    override fun parseHourlyForecast(response: JSONObject): Array<OneHourForecastPOJO?> {
        val jsonArray = response.getJSONArray(PARSER_KEY_DATA)
        val hourlyForecasts: Array<OneHourForecastPOJO?> = arrayOfNulls(48)
        for (i in hourlyForecasts.indices) {
            val jsonObject = jsonArray[i] as JSONObject
            val weather = parseWeatherState(jsonObject.getJSONObject(PARSER_KEY_WEATHER))
            val pod = jsonObject.getString(PARSER_KEY_POD)
            val date = jsonObject.getLong(PARSER_KEY_TS)
            val temp = jsonObject.getDouble(PARSER_KEY_TEMP)
            val pressure = jsonObject.getDouble(PARSER_KEY_PRES)
            val humidity = jsonObject.getDouble(PARSER_KEY_RH)
            val windSpeed = jsonObject.getDouble(PARSER_KEY_WIND_SPD)
            hourlyForecasts[i] = OneHourForecastPOJO(weather, pod, date, temp, pressure, humidity, windSpeed)
        }
        return hourlyForecasts
    }

    override fun parseDailyForecast(response: JSONObject): Array<OneDayForecastPOJO?> {
        val jsonArray = response.getJSONArray(PARSER_KEY_DATA)
        val dailyForecasts: Array<OneDayForecastPOJO?> = arrayOfNulls(16)
        for (i in dailyForecasts.indices) {
            val jsonObject = jsonArray[i] as JSONObject
            val weather = parseWeatherState(jsonObject.getJSONObject(PARSER_KEY_WEATHER))
            val date = jsonObject.getLong(PARSER_KEY_TS)
            val temp = jsonObject.getDouble(PARSER_KEY_TEMP)
            val pressure = jsonObject.getDouble(PARSER_KEY_PRES)
            val humidity = jsonObject.getDouble(PARSER_KEY_RH)
            val windSpeed = jsonObject.getDouble(PARSER_KEY_WIND_SPD)
            dailyForecasts[i] = OneDayForecastPOJO(weather, date, temp, pressure, humidity, windSpeed)
        }
        return dailyForecasts
    }

    private fun parseWeatherState(jsonObject: JSONObject): Weather {
        return Weather(jsonObject.getInt(PARSER_KEY_CODE), jsonObject.getString(PARSER_KEY_DESCRIPTION))
//        return when (jsonObject.getInt("code")) {
//            //thunderstorm
//            in 200..233 -> 700
//            //rain
//            300, 301, 302, 500, 501, 502, 511 -> 600
//            //shower rain
//            520, 521, 522 -> 500
//            //snow
//            in 600..623 -> 800
//            //mist
//            700, 711, 721, 731, 741, 751 -> 900
//            //clear sky
//            800 -> 100
//            //few clouds
//            801, 802 -> 200
//            //scattered clouds
//            803 -> 300
//            //broken clouds
//            804 -> 400
//            //unknown weather
//            else -> 1000
//        }
    }
}