package weatherServer.service

import org.json.JSONObject
import weatherClient.RequestMediator
import spark.Request
import spark.Response
import weatherServer.request.ClientRequestFactory


class ForecastService(private val requestManager: RequestMediator, private val clientRequestFactory: ClientRequestFactory) {

    fun serveRequest(request: Request, response: Response): JSONObject? {
        println(request.pathInfo())
        return requestManager.getForecast(clientRequestFactory.createRequest(request, response))
    }
}