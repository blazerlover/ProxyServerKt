package weatherClient.jsonParser

import org.json.JSONObject
import weatherClient.pojo.CurrentWeatherPOJO
import weatherClient.pojo.HourForecastPOJO
import weatherClient.pojo.DayForecastPOJO
import weatherClient.pojo.Weather
import weatherServer.*

class JSONParserWeatherbit(private val windDirectionConverter: WindDirectionConverter) : JSONParser {
    override fun parseCurrentWeather(response: JSONObject): CurrentWeatherPOJO {
        val jsonObject = response.getJSONArray(PARSER_KEY_DATA).getJSONObject(0)
        val weather = parseWeatherState(jsonObject.getJSONObject(PARSER_KEY_WEATHER))
        val pod = jsonObject.getString(PARSER_KEY_POD)
        val temp = jsonObject.getDouble(PARSER_KEY_TEMP)
        val feelLike = jsonObject.getDouble(PARSER_KEY_APP_TEMP)
        val pressure = jsonObject.getDouble(PARSER_KEY_PRES)
        val humidity = jsonObject.getDouble(PARSER_KEY_RH)
        val windSpeed = jsonObject.getDouble(PARSER_KEY_WIND_SPD)
        val windDir = windDirectionConverter.windDirCalculate(jsonObject.getInt(PARSER_KEY_WIND_DIR))
        val uvIndex = jsonObject.getInt(PARSER_KEY_UV)
        val visibility = jsonObject.getInt(PARSER_KEY_VIS)
        val dewPoint = jsonObject.getDouble(PARSER_KEY_DEWPT)
        return CurrentWeatherPOJO(weather, pod, temp, feelLike, pressure, humidity, windSpeed, windDir, uvIndex, visibility, dewPoint)
    }

    override fun parseHourlyForecast(response: JSONObject): Array<HourForecastPOJO?> {
        val jsonArray = response.getJSONArray(PARSER_KEY_DATA)
        val hourlyForecasts: Array<HourForecastPOJO?> = arrayOfNulls(48)
        for (i in hourlyForecasts.indices) {
            val jsonObject = jsonArray[i] as JSONObject
            val weather = parseWeatherState(jsonObject.getJSONObject(PARSER_KEY_WEATHER))
            val pod = jsonObject.getString(PARSER_KEY_POD)
            val date = jsonObject.getLong(PARSER_KEY_TS)
            val temp = jsonObject.getDouble(PARSER_KEY_TEMP)
            val feelLike = jsonObject.getDouble(PARSER_KEY_APP_TEMP)
            val pressure = jsonObject.getDouble(PARSER_KEY_PRES)
            val humidity = jsonObject.getDouble(PARSER_KEY_RH)
            val windSpeed = jsonObject.getDouble(PARSER_KEY_WIND_SPD)
            hourlyForecasts[i] = HourForecastPOJO(weather, pod, date, temp, feelLike, pressure, humidity, windSpeed)
        }
        return hourlyForecasts
    }

    override fun parseDailyForecast(response: JSONObject): Array<DayForecastPOJO?> {
        val jsonArray = response.getJSONArray(PARSER_KEY_DATA)
        val dailyForecasts: Array<DayForecastPOJO?> = arrayOfNulls(16)
        for (i in dailyForecasts.indices) {
            val jsonObject = jsonArray[i] as JSONObject
            val weather = parseWeatherState(jsonObject.getJSONObject(PARSER_KEY_WEATHER))
            val date = jsonObject.getLong(PARSER_KEY_TS)
            val maxTemp = jsonObject.getDouble(PARSER_KEY_MAX_TEMP)
            val minTemp = jsonObject.getDouble(PARSER_KEY_MIN_TEMP)
            val maxTempFeelLike = jsonObject.getDouble(PARSER_KEY_APP_MAX_TEMP)
            val minTempFeelLike = jsonObject.getDouble(PARSER_KEY_APP_MIN_TEMP)
            val pressure = jsonObject.getDouble(PARSER_KEY_PRES)
            val humidity = jsonObject.getDouble(PARSER_KEY_RH)
            val windSpeed = jsonObject.getDouble(PARSER_KEY_WIND_SPD)
            val windDir = jsonObject.getString(PARSER_KEY_WIND_CDIR_FULL)
            val pop = jsonObject.getInt(PARSER_KEY_POP)
            dailyForecasts[i] = DayForecastPOJO(weather, date, maxTemp, minTemp, maxTempFeelLike, minTempFeelLike,
                    pressure, humidity, windSpeed, windDir, pop)
        }
        return dailyForecasts
    }

    private fun parseWeatherState(jsonObject: JSONObject): Weather {
        return Weather(jsonObject.getInt(PARSER_KEY_CODE), jsonObject.getString(PARSER_KEY_DESCRIPTION))
    }
}