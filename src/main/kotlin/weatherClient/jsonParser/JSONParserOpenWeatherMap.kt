package weatherClient.jsonParser

import org.json.JSONObject
import weatherClient.pojo.*
import weatherServer.*

class JSONParserOpenWeatherMap(private val windDirectionConverter: WindDirectionConverter) : JSONParser {

    override fun parseCurrentWeather(response: JSONObject): CurrentWeatherPOJO {
        val jsonObject = response.getJSONObject(PARSER_KEY_CURRENT)
        val weather = parseWeatherState(response, PARSER_KEY_CURRENT)
        val pod = parsePOD(response, PARSER_KEY_CURRENT)
        val temp = jsonObject.getDouble(PARSER_KEY_TEMP)
        val feelLike = jsonObject.getDouble(PARSER_KEY_FEELS_LIKE)
        val pressure = jsonObject.getDouble(PARSER_KEY_PRESSURE)
        val humidity = jsonObject.getDouble(PARSER_KEY_HUMIDITY)
        val windSpeed = jsonObject.getDouble(PARSER_KEY_WIND_SPEED)
        val windDir = windDirectionConverter.windDirCalculate(jsonObject.getInt(PARSER_KEY_WIND_DEG))
        val uvIndex = jsonObject.getInt(PARSER_KEY_UVI)
        val visibility = jsonObject.getInt(PARSER_KEY_VISIBILITY)
        val dewPoint = jsonObject.getDouble(PARSER_KEY_DEW_POINT)
        return CurrentWeatherPOJO(weather, pod, temp, feelLike, pressure, humidity, windSpeed, windDir, uvIndex, visibility, dewPoint)
    }

    override fun parseHourlyForecast(response: JSONObject): Array<HourForecastPOJO?> {
        val jsonArray = response.getJSONArray(PARSER_KEY_HOURLY)
        val hourlyForecasts: Array<HourForecastPOJO?> = arrayOfNulls(48)
        for (i in hourlyForecasts.indices) {
            val jsonObject = jsonArray[i] as JSONObject
            val weather = parseWeatherState(jsonObject, PARSER_KEY_HOURLY)
            val pod = parsePOD(jsonObject, PARSER_KEY_HOURLY)
            val date = jsonObject.getLong(PARSER_KEY_DT)
            val temp = jsonObject.getDouble(PARSER_KEY_TEMP)
            val feelLike = jsonObject.getDouble(PARSER_KEY_FEELS_LIKE)
            val pressure = jsonObject.getDouble(PARSER_KEY_PRESSURE)
            val humidity = jsonObject.getDouble(PARSER_KEY_HUMIDITY)
            val windSpeed = jsonObject.getDouble(PARSER_KEY_WIND_SPEED)
            hourlyForecasts[i] = HourForecastPOJO(weather, pod, date, temp, feelLike, pressure, humidity, windSpeed)
        }
        return hourlyForecasts
    }

    override fun parseDailyForecast(response: JSONObject): Array<DayForecastPOJO?> {
        val jsonArray = response.getJSONArray(PARSER_KEY_DAILY)
        val dailyForecasts: Array<DayForecastPOJO?> = arrayOfNulls(7)
        for (i in dailyForecasts.indices) {
            val jsonObject = jsonArray[i] as JSONObject
            val weather = parseWeatherState(jsonObject, PARSER_KEY_DAILY)
            val date = jsonObject.getLong(PARSER_KEY_DT)
            val maxTemp = jsonObject.getJSONObject(PARSER_KEY_TEMP).getDouble(PARSER_KEY_MAX)
            val minTemp = jsonObject.getJSONObject(PARSER_KEY_TEMP).getDouble(PARSER_KEY_MIN)
            val maxTempFeelLike = jsonObject.getJSONObject(PARSER_KEY_FEELS_LIKE).getDouble(PARSER_KEY_DAY)
            val minTempFeelLike = jsonObject.getJSONObject(PARSER_KEY_FEELS_LIKE).getDouble(PARSER_KEY_NIGHT)
            val pressure = jsonObject.getDouble(PARSER_KEY_PRESSURE)
            val humidity = jsonObject.getDouble(PARSER_KEY_HUMIDITY)
            val windSpeed = jsonObject.getDouble(PARSER_KEY_WIND_SPEED)
            val windDir = windDirectionConverter.windDirCalculate(jsonObject.getInt(PARSER_KEY_WIND_DEG))
            val pop = 20
            dailyForecasts[i] = DayForecastPOJO(weather, date, maxTemp, minTemp, maxTempFeelLike, minTempFeelLike,
                    pressure, humidity, windSpeed, windDir, pop)
        }
        return dailyForecasts
    }

    private fun parseWeatherState(jsonObject: JSONObject, parserKey: String): Weather {
        val lJSONObject = when (parserKey) {
            PARSER_KEY_CURRENT -> jsonObject.getJSONObject(PARSER_KEY_CURRENT).getJSONArray(PARSER_KEY_WEATHER).getJSONObject(0)
            else -> jsonObject.getJSONArray(PARSER_KEY_WEATHER).getJSONObject(0)
        }
        return Weather(lJSONObject.getInt(PARSER_KEY_ID), lJSONObject.getString(PARSER_KEY_DESCRIPTION))
    }

    private fun parsePOD(jsonObject: JSONObject, parserKey: String): String {
        val lJSONObject: JSONObject = when (parserKey) {
            PARSER_KEY_CURRENT -> jsonObject.getJSONObject(PARSER_KEY_CURRENT).getJSONArray(PARSER_KEY_WEATHER).getJSONObject(0)
            else -> jsonObject.getJSONArray(PARSER_KEY_WEATHER).getJSONObject(0)
        }
        val str = lJSONObject.getString("icon")
        return if (str.codePointAt(2) == 'd'.toInt()) {
            "d"
        } else {
            "n"
        }
    }
}