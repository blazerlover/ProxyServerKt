package weatherClient.jsonParser

import org.json.JSONObject
import weatherClient.pojo.*
import weatherServer.*
import java.net.IDN

class JSONParserOpenWeatherMap : JSONParser {

    override fun parseCurrentWeather(response: JSONObject): CurrentWeatherPOJO {
        val weather = parseWeatherState(response)
        val pod = parsePOD(response)
        val temp = response.getJSONObject(PARSER_KEY_MAIN).getDouble(PARSER_KEY_TEMP)
        val feelLike = response.getJSONObject(PARSER_KEY_MAIN).getDouble(PARSER_KEY_FEEL_LIKE)
        val pressure = response.getJSONObject(PARSER_KEY_MAIN).getDouble(PARSER_KEY_PRESSURE)
        val humidity = response.getJSONObject(PARSER_KEY_MAIN).getDouble(PARSER_KEY_HUMIDITY)
        val windSpeed = response.getJSONObject(PARSER_KEY_WIND).getDouble(PARSER_KEY_SPEED)
        return CurrentWeatherPOJO(weather, pod, temp, feelLike, pressure, humidity, windSpeed)
    }

    override fun parseHourlyForecast(response: JSONObject): Array<OneHourForecastPOJO?> {
        val jsonArray = response.getJSONArray(PARSER_KEY_HOURLY)
        val hourlyForecasts: Array<OneHourForecastPOJO?> = arrayOfNulls(48)
        for (i in hourlyForecasts.indices) {
            val jsonObject = jsonArray[i] as JSONObject
            val weather = parseWeatherState(jsonObject)
            val pod = parsePOD(jsonObject)
            val date = jsonObject.getLong(PARSER_KEY_DT)
            val temp = jsonObject.getDouble(PARSER_KEY_TEMP)
            val pressure = jsonObject.getDouble(PARSER_KEY_PRESSURE)
            val humidity = jsonObject.getDouble(PARSER_KEY_HUMIDITY)
            val windSpeed = jsonObject.getDouble(PARSER_KEY_WIND_SPEED)
            hourlyForecasts[i] = OneHourForecastPOJO(weather, pod, date, temp, pressure, humidity, windSpeed)
        }
        return hourlyForecasts
    }

    override fun parseDailyForecast(response: JSONObject): Array<OneDayForecastPOJO?> {
        val jsonArray = response.getJSONArray(PARSER_KEY_DAILY)
        val dailyForecasts: Array<OneDayForecastPOJO?> = arrayOfNulls(7)
        for (i in dailyForecasts.indices) {
            val jsonObject = jsonArray[i] as JSONObject
            val weather = parseWeatherState(jsonObject)
            val date = jsonObject.getLong(PARSER_KEY_DT)
            val eveTemp = jsonObject.getJSONObject(PARSER_KEY_TEMP).getDouble(PARSER_KEY_EVE)
            val pressure = jsonObject.getDouble(PARSER_KEY_PRESSURE)
            val humidity = jsonObject.getDouble(PARSER_KEY_HUMIDITY)
            val windSpeed = jsonObject.getDouble(PARSER_KEY_WIND_SPEED)
            dailyForecasts[i] = OneDayForecastPOJO(weather, date, eveTemp, pressure, humidity, windSpeed)
        }
        return dailyForecasts
    }

    private fun parseWeatherState(jsonObject: JSONObject): Weather {
        val lJSONObject = jsonObject.getJSONArray(PARSER_KEY_WEATHER).getJSONObject(0)
        return Weather(lJSONObject.getInt(PARSER_KEY_ID), lJSONObject.getString(PARSER_KEY_DESCRIPTION))
    }

    private fun parsePOD(jsonObject: JSONObject):String {
        val lJSONObject = jsonObject.getJSONArray(PARSER_KEY_WEATHER).getJSONObject(0)
        val str = lJSONObject.getString("icon")
        return if (str.codePointAt(2) == 'd'.toInt()) {
            "d"
        } else {
            "n"
        }
    }
}