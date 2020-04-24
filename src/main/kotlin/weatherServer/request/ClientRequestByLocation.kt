package weatherServer.request

class ClientRequestByLocation(override val requestParams: String, override val requestType: String, var latitude: Double, var longitude: Double): ClientRequest(requestParams, requestType)
