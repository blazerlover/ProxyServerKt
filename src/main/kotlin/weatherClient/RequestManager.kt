package weatherClient

import org.json.JSONObject
import weatherClient.jsonParser.JSONParser
import weatherClient.urlBuilder.URLBuilder
import weatherServer.CURRENT
import weatherServer.DAILY
import weatherServer.HOURLY
import weatherServer.request.ClientRequest

class RequestManager constructor(
    private val requestMaker: RequestMaker,
    private val urlBuilder: URLBuilder,
    private val jsonParser: JSONParser,
    private val jsonProvider: JSONProvider
) {

    fun getForecast(clientRequest: ClientRequest): JSONObject? {
        val response: JSONObject = requestMaker.doRequest(urlBuilder.buildURLBy(clientRequest))
        return makeClientResponse(response, clientRequest.requestType)
    }

    private fun makeClientResponse(response: JSONObject, requestType: String): JSONObject? {
        when (requestType) {
            CURRENT -> {
                val currentWeatherPOJO = jsonParser.parseCurrentWeather(response)
                return jsonProvider.getCurrentWeatherJSON(currentWeatherPOJO)
            }
            HOURLY -> {
                val hourlyForecasts = jsonParser.parseHourlyForecast(response)
                return jsonProvider.getHourlyForecastJSON(hourlyForecasts)
            }
            DAILY -> {
                val dailyForecasts = jsonParser.parseDailyForecast(response)
                return jsonProvider.getDailyForecastJSON(dailyForecasts)
            }
        }
        return null
    }
}