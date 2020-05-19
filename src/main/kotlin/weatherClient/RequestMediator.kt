package weatherClient

import org.json.JSONObject
import weatherClient.jsonParser.JSONParser
import weatherClient.urlBuilder.URLBuilder
import weatherServer.REQUEST_TYPE_VALUE_CURRENT
import weatherServer.REQUEST_TYPE_VALUE_DAILY
import weatherServer.REQUEST_TYPE_VALUE_HOURLY
import weatherServer.request.ClientRequest

class RequestMediator constructor(
    private val requestMaker: RequestMaker,
    val urlBuilder: URLBuilder,
    val jsonParser: JSONParser,
    private val jsonProvider: JSONProvider
) {

    fun getForecast(clientRequest: ClientRequest): JSONObject? {
        val response: JSONObject = requestMaker.doRequest(urlBuilder.buildURLBy(clientRequest))
        return makeClientResponse(response, clientRequest.requestType)
    }

    private fun makeClientResponse(response: JSONObject, requestType: String): JSONObject? {
        when (requestType) {
            REQUEST_TYPE_VALUE_CURRENT -> {
                val currentWeatherPOJO = jsonParser.parseCurrentWeather(response)
                return jsonProvider.getCurrentWeatherJSON(currentWeatherPOJO)
            }
            REQUEST_TYPE_VALUE_HOURLY -> {
                val hourlyForecasts = jsonParser.parseHourlyForecast(response)
                return jsonProvider.getHourlyForecastJSON(hourlyForecasts)
            }
            REQUEST_TYPE_VALUE_DAILY -> {
                val dailyForecasts = jsonParser.parseDailyForecast(response)
                return jsonProvider.getDailyForecastJSON(dailyForecasts)
            }
        }
        return null
    }
}