package weatherServer.request

class ClientRequestByCityName(override val requestParams: String, override val requestType: String, var cityName: String): ClientRequest(requestParams, requestType)