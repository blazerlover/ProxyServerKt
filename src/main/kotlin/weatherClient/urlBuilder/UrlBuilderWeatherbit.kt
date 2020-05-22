package weatherClient.urlBuilder

import weatherServer.*
import weatherServer.request.ClientRequest
import weatherServer.request.ClientRequestByCityName
import weatherServer.request.ClientRequestByLocation
import java.lang.StringBuilder
import java.net.MalformedURLException
import java.net.URL

class UrlBuilderWeatherbit : UrlBuilder {

    override fun buildURLBy(clientRequest: ClientRequest): URL {
        return buildSelector(clientRequest)
    }

    private fun buildSelector(clientRequest: ClientRequest): URL {
        val requestType: String = when (clientRequest.requestType) {
            REQUEST_TYPE_VALUE_CURRENT -> {
                WEATHERBIT_CURRENT_REQUEST_TYPE
            }
            REQUEST_TYPE_VALUE_HOURLY -> {
                WEATHERBIT_HOURLY_REQUEST_TYPE
            }
            else -> {
                WEATHERBIT_DAILY_REQUEST_TYPE
            }
        }
        return when (clientRequest.requestParams) {
            REQUEST_PARAM_BY_LOCATION -> {
                clientRequest as ClientRequestByLocation
                buildURLByLocation(requestType, clientRequest.latitude, clientRequest.longitude)
            }
            else -> {
                clientRequest as ClientRequestByCityName
                buildURLByCityName(requestType, clientRequest.cityName)
            }
        }
    }

    private fun buildURLByLocation(requestType: String, latitude: Double, longitude: Double): URL {
        var url: URL? = null
        val sLatitude = "lat=$latitude"
        val sLongitude = "lon=$longitude"

        val builder = StringBuilder()
        builder.append(WEATHERBIT_PATH)
                .append(requestType)
                .append(sLatitude)
                .append("&")
                .append(sLongitude)
                .append(WEATHERBIT_API_KEY)
        try {
            url = URL(builder.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        println(url)
        return url!!
    }

    private fun buildURLByCityName(requestType: String, cityName: String): URL {
        var url: URL? = null
        val sCityName = "city=$cityName"

        val builder = StringBuilder()
        builder.append(WEATHERBIT_PATH)
                .append(requestType)
                .append(sCityName)
                .append(WEATHERBIT_API_KEY)
        try {
            url = URL(builder.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        println(url)
        return url!!
    }
}