package weatherClient.jsonParser

import org.json.JSONObject
import weatherClient.pojo.CurrentWeatherPOJO
import weatherClient.pojo.DailyForecastPOJO
import weatherClient.pojo.HourlyForecastPOJO
import weatherClient.pojo.Weather

class JSONParserOpenWeatherMap : JSONParser {

    override fun parseCurrentWeather(response: JSONObject): CurrentWeatherPOJO {
        val weather = parseWeatherState(response)
        val pod = parsePOD(response)
        val temp = response.getJSONObject("main").getDouble("temp")
        val feelLike = response.getJSONObject("main").getDouble("feels_like")
        val pressure = response.getJSONObject("main").getDouble("pressure")
        val humidity = response.getJSONObject("main").getDouble("humidity")
        val windSpeed = response.getJSONObject("wind").getDouble("speed")
        return CurrentWeatherPOJO(weather, pod, temp, feelLike, pressure, humidity, windSpeed)
    }

    override fun parseHourlyForecast(response: JSONObject): HourlyForecastPOJO {
        val jsonArray = response.getJSONArray("hourly")
        val hourlyForecasts: Array<HourlyForecastPOJO.HourlyForecast?> = arrayOfNulls(7)
        for (i in hourlyForecasts.indices) {
            val jsonObject = jsonArray[i] as JSONObject
            val weather = parseWeatherState(jsonObject)
            val pod = parsePOD(jsonObject)
            val date = jsonObject.getLong("dt")
            val temp = jsonObject.getDouble("temp")
            val pressure = jsonObject.getDouble("pressure")
            val humidity = jsonObject.getDouble("humidity")
            val windSpeed = jsonObject.getDouble("wind_speed")
            hourlyForecasts[i] = HourlyForecastPOJO.HourlyForecast(weather, pod, date, temp, pressure, humidity, windSpeed)
        }
        return HourlyForecastPOJO(hourlyForecasts)
    }

    override fun parseDailyForecast(response: JSONObject): DailyForecastPOJO {
        val jsonArray = response.getJSONArray("daily")
        val dailyForecasts: Array<DailyForecastPOJO.DailyForecast?> = arrayOfNulls(7)
        for (i in dailyForecasts.indices) {
            val jsonObject = jsonArray[i] as JSONObject
            val weather = parseWeatherState(jsonObject)
            val date = jsonObject.getLong("dt")
            val eveTemp = jsonObject.getJSONObject("temp").getDouble("eve")
            val pressure = jsonObject.getDouble("pressure")
            val humidity = jsonObject.getDouble("humidity")
            val windSpeed = jsonObject.getDouble("wind_speed")
            dailyForecasts[i] = DailyForecastPOJO.DailyForecast(weather, date, eveTemp, pressure, humidity, windSpeed)
        }
        return DailyForecastPOJO(dailyForecasts)
    }

    private fun parseWeatherState(jsonObject: JSONObject): Weather {
        val lJSONObject = jsonObject.getJSONArray("weather").getJSONObject(0)
        return Weather(lJSONObject.getInt("id"), lJSONObject.getString("description"))
    }

    private fun parsePOD(jsonObject: JSONObject):String {
        val lJSONObject = jsonObject.getJSONArray("weather").getJSONObject(0)
        val str = lJSONObject.getString("icon")
        return if (str.codePointAt(2) == 'd'.toInt()) {
            "d"
        } else {
            "n"
        }
    }
}