package weatherServer.request

abstract class ClientRequest(open val requestParams: String, open val requestType: String)