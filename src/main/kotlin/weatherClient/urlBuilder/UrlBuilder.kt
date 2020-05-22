package weatherClient.urlBuilder

import weatherServer.request.ClientRequest
import java.net.URL

interface UrlBuilder {
    fun buildURLBy(clientRequest: ClientRequest): URL
}