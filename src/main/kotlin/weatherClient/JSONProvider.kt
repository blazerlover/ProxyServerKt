package weatherClient

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import weatherClient.pojo.CurrentWeatherPOJO
import weatherClient.pojo.DayForecastPOJO
import weatherClient.pojo.HourForecastPOJO
import weatherClient.pojo.Weather
import weatherServer.*

class JSONProvider {

    var jsonObject = JSONObject()

    fun getCurrentWeatherJSON(currentWeatherPOJO: CurrentWeatherPOJO): JSONObject {
        jsonObject = JSONObject()

        jsonObject.put(PARSER_KEY_WEATHER, weatherToJSON(currentWeatherPOJO.weather))
                .put(PARSER_KEY_POD, currentWeatherPOJO.pod)
                .put(PARSER_KEY_TEMP, currentWeatherPOJO.temp)
                .put(PARSER_KEY_FEEL_LIKE, currentWeatherPOJO.feelLike)
                .put(PARSER_KEY_PRESSURE, currentWeatherPOJO.pressure)
                .put(PARSER_KEY_HUMIDITY, currentWeatherPOJO.humidity)
                .put(PARSER_KEY_WIND_SPEED, currentWeatherPOJO.windSpeed)
        return jsonObject
    }

    fun getHourlyForecastJSON(hourlyForecasts: Array<HourForecastPOJO?>): JSONObject {
        val jsonArray = JSONArray()
        jsonObject = JSONObject()
        for (i in hourlyForecasts.indices) {
            val lJsonObject = JSONObject()
            lJsonObject.put(PARSER_KEY_WEATHER, weatherToJSON(hourlyForecasts[i]?.weather))
                    .put(PARSER_KEY_POD, hourlyForecasts[i]?.pod)
                    .put(PARSER_KEY_DATE, hourlyForecasts[i]?.date)
                    .put(PARSER_KEY_TEMP, hourlyForecasts[i]?.temp)
                    .put(PARSER_KEY_FEEL_LIKE, hourlyForecasts[i]?.feelLike)
                    .put(PARSER_KEY_PRESSURE, hourlyForecasts[i]?.pressure)
                    .put(PARSER_KEY_HUMIDITY, hourlyForecasts[i]?.humidity)
                    .put(PARSER_KEY_WIND_SPEED, hourlyForecasts[i]?.windSpeed)
            jsonArray.put(i, lJsonObject)
        }
        try {
            jsonObject.put(PARSER_KEY_LIST, jsonArray)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return jsonObject
    }

    fun getDailyForecastJSON(dailyForecasts: Array<DayForecastPOJO?>): JSONObject {
        val jsonArray = JSONArray()
        jsonObject = JSONObject()
        for (i in dailyForecasts.indices) {
            val lJsonObject = JSONObject()
            lJsonObject.put(PARSER_KEY_WEATHER, weatherToJSON(dailyForecasts[i]?.weather))
                    .put(PARSER_KEY_DATE, dailyForecasts[i]?.date)
                    .put(PARSER_KEY_MAX_TEMP, dailyForecasts[i]?.maxTemp)
                    .put(PARSER_KEY_MIN_TEMP, dailyForecasts[i]?.minTemp)
                    .put(PARSER_KEY_FEEL_LIKE_MAX_TEMP, dailyForecasts[i]?.maxTempFeelLike)
                    .put(PARSER_KEY_FEEL_LIKE_MIX_TEMP, dailyForecasts[i]?.minTempFeelLike)
                    .put(PARSER_KEY_PRESSURE, dailyForecasts[i]?.pressure)
                    .put(PARSER_KEY_HUMIDITY, dailyForecasts[i]?.humidity)
                    .put(PARSER_KEY_WIND_SPEED, dailyForecasts[i]?.windSpeed)
            jsonArray.put(i, lJsonObject)
        }
        try {
            jsonObject.put(PARSER_KEY_LIST, jsonArray)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return jsonObject
    }

    private fun weatherToJSON(weather: Weather?): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put(PARSER_KEY_CODE, weather?.code)
        jsonObject.put(PARSER_KEY_DESCRIPTION, weather?.description)
        return jsonObject
    }
}