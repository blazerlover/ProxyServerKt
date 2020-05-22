package weatherServer.request

class ClientRequestByCityName(override val requestParams: String, override val requestType: String, val cityName: String): ClientRequest(requestParams, requestType) {
    override fun toString(): String {
        return "ClientRequestByCityName(requestParams='$requestParams', requestType='$requestType', cityName='$cityName')"
    }
}