package weatherServer

import spark.Spark.get
import weatherClient.JSONProvider
import weatherClient.RequestMaker
import weatherClient.RequestManager
import weatherClient.jsonParser.JSONParserWeatherbit
import weatherClient.urlBuilder.URLBuilder
import weatherClient.urlBuilder.URLBuilderWeatherbit
import weatherServer.path.FORECAST_PARAMS_PATH
import weatherServer.path.FORECAST_PATH
import weatherServer.request.ClientRequestByLocation
import weatherServer.request.ClientRequestFactory
import weatherServer.service.ForecastService

class ProxyServer {

    var requestMaker = RequestMaker()
    var jsonProvider = JSONProvider()
    var urlBuilder: URLBuilder = URLBuilderWeatherbit()
    var jsonParser = JSONParserWeatherbit()

    fun go() {
        val requestManager = RequestManager(requestMaker, urlBuilder, jsonParser, jsonProvider)
        val clientRequestFactory = ClientRequestFactory()
        val forecastService = ForecastService(requestManager, clientRequestFactory)
//        test
        val jsonObject3 = requestManager.getForecast(ClientRequestByLocation(REQUEST_PARAM_BY_LOCATION,
                "current",60.0494, 30.4458))
//        val jsonObject4 = requestManager.getForecast(ClientRequestByCityName(REQUEST_PARAM_BY_CITY_NAME,
//                "current","London"))

        println(jsonObject3)
//        println(jsonObject4)

        get(FORECAST_PATH, forecastService::serveRequest)
        get(FORECAST_PARAMS_PATH) { request, response -> forecastService.serveRequest(request, response) }
    }
}