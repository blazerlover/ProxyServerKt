package weatherServer.service

import org.json.JSONObject
import weatherClient.RequestManager
import spark.Request
import spark.Response
import weatherServer.LATITUDE_KEY
import weatherServer.LONGITUDE_KEY
import weatherServer.REQUEST_TYPE_KEY
import weatherServer.request.ClientRequestByLocation
import weatherServer.request.ClientRequestFactory


class ForecastService(private val requestManager: RequestManager, private val clientRequestFactory: ClientRequestFactory) {

    fun serveRequest(request: Request, response: Response): JSONObject? {
        println(request.pathInfo())
        return requestManager.getForecast(clientRequestFactory.createRequest(request, response))
    }

//    private fun createClientRequest(request: Request, response: Response): ClientRequestByLocation {
//        val latitude = request.queryMap().get(LATITUDE_KEY).doubleValue()
//        val longitude = request.queryMap().get(LONGITUDE_KEY).doubleValue()
//        val requestType = request.queryMap().get(REQUEST_TYPE_KEY).value()
//        return ClientRequestByLocation(latitude, longitude, requestType)
//    }
}