package weatherServer

import spark.Spark.get
import weatherClient.JSONProvider
import weatherClient.RequestMaker
import weatherClient.RequestMediator
import weatherClient.jsonParser.JSONParserWeatherbit
import weatherClient.jsonParser.WindDirectionConverter
import weatherClient.urlBuilder.UrlBuilderWeatherbit
import weatherServer.path.FORECAST_PARAMS_PATH
import weatherServer.request.ClientRequestFactory
import weatherServer.service.ForecastService

class ProxyServer {

    var requestMaker = RequestMaker()
    var jsonProvider = JSONProvider()
    var windDirectionConverter = WindDirectionConverter()
    var urlBuilder = UrlBuilderWeatherbit()
    var jsonParser = JSONParserWeatherbit(windDirectionConverter)
//    2ой источник:
//    var urlBuilder = URLBuilderOpenWeatherMap()
//    var jsonParser = JSONParserOpenWeatherMap(windDirectionConverter)

    fun go() {
        val requestMediator = RequestMediator(requestMaker, urlBuilder, jsonParser, jsonProvider)
        val clientRequestFactory = ClientRequestFactory()
        val forecastService = ForecastService(requestMediator, clientRequestFactory)
//        test
//        val jsonObject3 = requestMediator.getForecast(ClientRequestByLocation(REQUEST_PARAM_BY_LOCATION,
//                "hourly",60.0494, 130.4458))
//        val jsonObject4 = requestMediator.getForecast(ClientRequestByCityName(REQUEST_PARAM_BY_CITY_NAME,
//                "current","Jakarta"))

        get(FORECAST_PARAMS_PATH) { request, response -> forecastService.serveRequest(request, response) }
    }
}