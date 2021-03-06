package weatherServer.request

import spark.Request
import spark.Response
import weatherServer.*

class ClientRequestFactory {
    fun createRequest(request: Request, response: Response): ClientRequest {
        val clientRequest: ClientRequest
        return when (request.params(":by")) {
            REQUEST_PARAM_BY_LOCATION -> {
                val latitude = request.queryMap().get(LATITUDE_KEY).doubleValue()
                val longitude = request.queryMap().get(LONGITUDE_KEY).doubleValue()
                val requestType = request.queryMap().get(REQUEST_TYPE_KEY).value()
                clientRequest = ClientRequestByLocation(REQUEST_PARAM_BY_LOCATION, requestType, latitude, longitude)
                println(clientRequest)
                clientRequest
            }
            else -> {
                val cityName = request.queryMap().get(CITY_NAME_KEY).value()
                val requestType = request.queryMap().get(REQUEST_TYPE_KEY).value()
                clientRequest = ClientRequestByCityName(REQUEST_PARAM_BY_CITY_NAME, requestType, cityName)
                println(clientRequest)
                clientRequest
            }
        }
    }
}