package weatherClient.urlBuilder

import weatherServer.request.ClientRequest
import java.net.URL

interface URLBuilder {
    fun buildURLBy(clientRequest: ClientRequest): URL
}