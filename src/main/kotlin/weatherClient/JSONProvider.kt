package weatherClient

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import weatherClient.pojo.CurrentWeatherPOJO
import weatherClient.pojo.DailyForecastPOJO
import weatherClient.pojo.HourlyForecastPOJO
import weatherClient.pojo.Weather

class JSONProvider {

    var jsonObject = JSONObject()

    fun getCurrentWeatherJSON(currentWeatherPOJO: CurrentWeatherPOJO): JSONObject {
        jsonObject = JSONObject()

        jsonObject.put("weather", toJSON(currentWeatherPOJO.weather))
                .put("pod", currentWeatherPOJO.pod)
                .put("temp", currentWeatherPOJO.temp)
                .put("feelLike", currentWeatherPOJO.feelLike)
                .put("pressure", currentWeatherPOJO.pressure)
                .put("humidity", currentWeatherPOJO.humidity)
                .put("windSpeed", currentWeatherPOJO.windSpeed)
        return jsonObject
    }

    fun getHourlyForecastJSON(hourlyForecastPOJO: HourlyForecastPOJO): JSONObject {
        val jsonArray = JSONArray()
        jsonObject = JSONObject()
        val hourlyForecasts: Array<HourlyForecastPOJO.HourlyForecast?> = hourlyForecastPOJO.hourlyForecasts
        for (i in hourlyForecasts.indices) {
            val lJsonObject = JSONObject()
            lJsonObject.put("weather", toJSON(hourlyForecasts[i]?.weather))
                    .put("pod", hourlyForecasts[i]?.pod)
                    .put("dt", hourlyForecasts[i]?.date)
                    .put("eve", hourlyForecasts[i]?.temp)
                    .put("pressure", hourlyForecasts[i]?.pressure)
                    .put("humidity", hourlyForecasts[i]?.humidity)
                    .put("speed", hourlyForecasts[i]?.windSpeed)
            jsonArray.put(i, lJsonObject)
        }
        try {
            jsonObject.put("list", jsonArray)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return jsonObject
    }

    fun getDailyForecastJSON(dailyForecastPOJO: DailyForecastPOJO): JSONObject {
        val jsonArray = JSONArray()
        jsonObject = JSONObject()
        val dailyForecasts: Array<DailyForecastPOJO.DailyForecast?> = dailyForecastPOJO.dailyForecasts
        for (i in dailyForecasts.indices) {
            val lJsonObject = JSONObject()
            lJsonObject.put("weather",toJSON(dailyForecasts[i]?.weather))
                    .put("dt", dailyForecasts[i]?.date)
                    .put("eve", dailyForecasts[i]?.temp)
                    .put("pressure", dailyForecasts[i]?.pressure)
                    .put("humidity", dailyForecasts[i]?.humidity)
                    .put("speed", dailyForecasts[i]?.windSpeed)
            jsonArray.put(i, lJsonObject)
        }
        try {
            jsonObject.put("list", jsonArray)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return jsonObject
    }

    private fun toJSON(weather: Weather?): JSONObject {
        var jsonObject = JSONObject()
        jsonObject.put("code", weather?.code)
        jsonObject.put("description", weather?.description)
        return jsonObject
    }
}