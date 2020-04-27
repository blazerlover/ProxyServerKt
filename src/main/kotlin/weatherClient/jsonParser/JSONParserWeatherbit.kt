package weatherClient.jsonParser

import org.json.JSONObject
import weatherClient.pojo.CurrentWeatherPOJO
import weatherClient.pojo.OneHourForecastPOJO
import weatherClient.pojo.OneDayForecastPOJO
import weatherClient.pojo.Weather

class JSONParserWeatherbit : JSONParser {
    override fun parseCurrentWeather(response: JSONObject): CurrentWeatherPOJO {
        val jsonObject = response.getJSONArray("data").getJSONObject(0)
        val weather = parseWeatherState(jsonObject.getJSONObject("weather"))
        val pod = jsonObject.getString("pod")
        val temp = jsonObject.getDouble("temp")
        val feelLike = jsonObject.getDouble("app_temp")
        val pressure = jsonObject.getDouble("pres")
        val humidity = jsonObject.getDouble("rh")
        val windSpeed = jsonObject.getDouble("wind_spd")
        return CurrentWeatherPOJO(weather, pod, temp, feelLike, pressure, humidity, windSpeed)
    }

    override fun parseHourlyForecast(response: JSONObject): Array<OneHourForecastPOJO?> {
        val jsonArray = response.getJSONArray("data")
        val hourlyForecasts: Array<OneHourForecastPOJO?> = arrayOfNulls(48)
        for (i in hourlyForecasts.indices) {
            val jsonObject = jsonArray[i] as JSONObject
            val weather = parseWeatherState(jsonObject.getJSONObject("weather"))
            val pod = jsonObject.getString("pod")
            val date = jsonObject.getLong("ts")
            val temp = jsonObject.getDouble("temp")
            val pressure = jsonObject.getDouble("pres")
            val humidity = jsonObject.getDouble("rh")
            val windSpeed = jsonObject.getDouble("wind_spd")
            hourlyForecasts[i] = OneHourForecastPOJO(weather, pod, date, temp, pressure, humidity, windSpeed)
        }
        return hourlyForecasts
    }

    override fun parseDailyForecast(response: JSONObject): Array<OneDayForecastPOJO?> {
        val jsonArray = response.getJSONArray("data")
        val dailyForecasts: Array<OneDayForecastPOJO?> = arrayOfNulls(16)
        for (i in dailyForecasts.indices) {
            val jsonObject = jsonArray[i] as JSONObject
            val weather = parseWeatherState(jsonObject.getJSONObject("weather"))
            val date = jsonObject.getLong("ts")
            val temp = jsonObject.getDouble("temp")
            val pressure = jsonObject.getDouble("pres")
            val humidity = jsonObject.getDouble("rh")
            val windSpeed = jsonObject.getDouble("wind_spd")
            dailyForecasts[i] = OneDayForecastPOJO(weather, date, temp, pressure, humidity, windSpeed)
        }
        return dailyForecasts
    }

    private fun parseWeatherState(jsonObject: JSONObject): Weather {
        return Weather(jsonObject.getInt("code"), jsonObject.getString("description"))
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