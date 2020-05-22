package weatherServer.request

class ClientRequestByLocation(override val requestParams: String, override val requestType: String, val latitude: Double, val longitude: Double) : ClientRequest(requestParams, requestType) {
    override fun toString(): String {
        return "ClientRequestByLocation(requestParams='$requestParams', requestType='$requestType', latitude=$latitude, longitude=$longitude)"
    }
}
